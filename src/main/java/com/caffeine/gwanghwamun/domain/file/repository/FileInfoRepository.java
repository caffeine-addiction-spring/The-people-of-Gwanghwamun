package com.caffeine.gwanghwamun.domain.file.repository;

import com.caffeine.gwanghwamun.domain.file.entity.FileInfo;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, UUID> {
	List<FileInfo> findByGid(String gid);

	List<FileInfo> findByGidAndLocation(String gid, String location);

	List<FileInfo> findByGidAndLocationAndDone(String gid, String location, boolean done);

	Page<FileInfo> findByGid(String gid, Pageable pageable);

	Page<FileInfo> findByGidAndLocation(String gid, String location, Pageable pageable);

	List<FileInfo> findByDone(boolean done);

	List<FileInfo> findByGidAndDone(String gid, boolean done);
}
