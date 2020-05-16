package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suke.czx.Application;
import com.suke.czx.common.utils.MPPageConvert;
import com.suke.czx.modules.subject.entity.TbSubject;
import com.suke.czx.modules.subject.service.TbSubjectService;
import net.sf.json.test.JSONAssert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TbSubjectTest {

    @Autowired
    private TbSubjectService tbSubjectService;

    @Autowired
    protected MPPageConvert mpPageConvert;

    @org.junit.Test
    public void test() {

        try {
            File file =new File("D:\\json.txt");

            byte[] bytes=new byte[1024*1024*5];

            FileInputStream fileInputStream=new FileInputStream(file);
            int i=fileInputStream.read(bytes);

            String result=new String(bytes,0,i);
            JSONArray jsonArray =JSONArray.parseArray(result.trim());
            TbSubject tbSubject=null;
            for (Object obj:jsonArray) {
                JSONObject jsonObject = JSONObject.parseObject(obj.toString());
                tbSubject=new TbSubject();
                tbSubject.setType(jsonObject.getString("type"));
                tbSubject.setRightChoice(jsonObject.getString("a"));
                tbSubject.setTitle(jsonObject.getString("q").length() > 10? jsonObject.getString("q").substring(0,10)+"....":jsonObject.getString("q"));
                tbSubject.setSubject(jsonObject.getString("q"));
                tbSubject.setIsanswer(Integer.valueOf(jsonObject.getString("isAnswer")));
                tbSubject.setOptions(jsonObject.getString("options"));
                tbSubject.setRightChoice(jsonObject.getString("a"));
                tbSubject.setCreateTime(new Date());
                tbSubject.setUpdateTime(new Date());
                tbSubjectService.save(tbSubject);
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
