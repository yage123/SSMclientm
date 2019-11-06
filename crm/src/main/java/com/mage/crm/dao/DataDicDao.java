package com.mage.crm.dao;

import com.mage.crm.vo.Customer;
import com.mage.crm.vo.DataDic;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DataDicDao {
    @Select("Select data_dic_value as dataDicValue from t_dataDic where data_dic_name=#{dataDicName} and is_valid=1")
    public List<DataDic> queryDataDicValueByDataDicName(String dataDicName);
}



