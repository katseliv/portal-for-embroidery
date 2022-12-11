package ru.vsu.portalforembroidery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.portalforembroidery.exception.EntityCreationException;
import ru.vsu.portalforembroidery.exception.EntityNotFoundException;
import ru.vsu.portalforembroidery.mapper.FileMapper;
import ru.vsu.portalforembroidery.model.dto.FileDto;
import ru.vsu.portalforembroidery.model.dto.view.FileViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.model.entity.FileEntity;
import ru.vsu.portalforembroidery.model.entity.FolderEntity;
import ru.vsu.portalforembroidery.repository.FileRepository;
import ru.vsu.portalforembroidery.repository.FolderRepository;
import ru.vsu.portalforembroidery.utils.ParseUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService, PaginationService<FileViewDto> {

    @Value("${pagination.defaultPageNumber}")
    private int defaultPageNumber;
    @Value("${pagination.defaultPageSize}")
    private int defaultPageSize;

    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;
    private final FileMapper fileMapper;

    @Override
    @Transactional
    public int createFile(FileDto fileDto) {
        final Optional<FolderEntity> folderEntity = folderRepository.findById(fileDto.getFolderId());
        folderEntity.ifPresentOrElse(
                (user) -> log.info("Folder has been found."),
                () -> {
                    log.warn("Folder hasn't been found.");
                    throw new EntityNotFoundException("Folder not found!");
                }
        );
        final FileEntity fileEntity = Optional.of(fileDto)
                .map(fileMapper::fileDtoToFileEntity)
                .map(fileRepository::save)
                .orElseThrow(() -> new EntityCreationException("File hasn't been created!"));
        log.info("File with id = {} has been created.", fileEntity.getId());
        return fileEntity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public FileViewDto getFileViewById(int id) {
        final Optional<FileEntity> fileEntity = fileRepository.findById(id);
        fileEntity.ifPresentOrElse(
                (file) -> log.info("File with id = {} has been found.", file.getId()),
                () -> log.warn("File hasn't been found."));
        return fileEntity.map(fileMapper::fileEntityToFileViewDto)
                .orElseThrow(() -> new EntityNotFoundException("File not found!"));
    }

    @Override
    @Transactional
    public void updateFileById(int id, FileDto FileDto) {
        final Optional<FileEntity> fileEntity = fileRepository.findById(id);
        fileEntity.ifPresentOrElse(
                (file) -> log.info("File with id = {} has been found.", file.getId()),
                () -> {
                    log.warn("File hasn't been found.");
                    throw new EntityNotFoundException("File not found!");
                });
        Optional.of(FileDto)
                .map(fileMapper::fileDtoToFileEntity)
                .map((file) -> {
                    file.setId(id);
                    return fileRepository.save(file);
                });
        log.info("File with id = {} has been updated.", id);
    }

    @Override
    @Transactional
    public void deleteFileById(int id) {
        final Optional<FileEntity> fileEntity = fileRepository.findById(id);
        fileEntity.ifPresentOrElse(
                (file) -> log.info("File with id = {} has been found.", file.getId()),
                () -> {
                    log.warn("File hasn't been found.");
                    throw new EntityNotFoundException("File not found!");
                });
        fileRepository.deleteById(id);
        log.info("File with id = {} has been deleted.", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ViewListPage<FileViewDto> getViewListPage(String page, String size) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<FileViewDto> listFiles = listFiles(pageable);
        final int totalAmount = numberOfFiles();

        return getViewListPage(totalAmount, pageSize, pageNumber, listFiles);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileViewDto> listFiles(Pageable pageable) {
        final List<FileEntity> fileEntities = fileRepository.findAll(pageable).getContent();
        log.info("There have been found {} files.", fileEntities.size());
        return fileMapper.fileEntitiesToFileViewDtoList(fileEntities);
    }

    @Override
    public int numberOfFiles() {
        final long numberOfFiles = fileRepository.count();
        log.info("There have been found {} files.", numberOfFiles);
        return (int) numberOfFiles;
    }

}