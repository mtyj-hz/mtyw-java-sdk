package com.mtyw.storage;

import com.mtyw.storage.model.response.ResultResponse;
import com.mtyw.storage.model.response.filecoin.FilecoinDateRes;
import org.junit.Test;

import java.util.List;

/**
 * @Author: xiaoli
 * @Date: 2021/1/5 4:15 下午
 */
public class FilecoinTest extends TestBase{

    @Test
    public  void filecoinDatelist() {
        ResultResponse<List<FilecoinDateRes>> resultResponse = mfssClient.filecoinDatelist();
        int aa =1;
        return ;
    }
}
