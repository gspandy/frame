package mb.mba.center.dao;

import mb.mba.core.entity.DataDirectoryEntity;

public interface DataDirectoryEntityMapper {
    int insert(DataDirectoryEntity record);

    int insertSelective(DataDirectoryEntity record);
}