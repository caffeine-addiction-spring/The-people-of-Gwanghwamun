package com.caffeine.gwanghwamun.domain.file.repository;

import com.caffeine.gwanghwamun.domain.file.entity.FileInfo;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, UUID> {
	List<FileInfo> findByGid(String gid);

	List<FileInfo> findByGidAndLocation(String gid, String location);

	List<FileInfo> findByGidAndLocationAndDone(String gid, String location, boolean done);
}
