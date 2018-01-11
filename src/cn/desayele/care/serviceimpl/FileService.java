package cn.desayele.care.serviceimpl;


import cn.desayele.care.entity.TFileEntity;
import cn.desayele.care.service.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @apiNote: 文件操作相关
 * @author dingfada
 * @date 2017.12.12
 */

@Service
public class FileService extends BaseServiceImpl {
	private static final Logger logger=Logger.getLogger(FileService.class);

    /**
     * @apiNote 根据外键获取文件列表
     * @return TStaffEntity
     */
    public List<TFileEntity> getTFileEntityListBySid(String sid){
        List<TFileEntity> l=queryPojoL("select * from t_file where sid='"+sid+"'",TFileEntity.class);
        return l;
    }
    /**
     * @apiNote 添加一条记录
     * @return
     */
    public void addTFileEntity(TFileEntity tf){
        add(tf);
    }
    /**
     * @apiNote 根据oid删除记录
     * @return
     */
    public void deleteTFileEntityByOid(TFileEntity tf){
        delete(tf);
    }
}
