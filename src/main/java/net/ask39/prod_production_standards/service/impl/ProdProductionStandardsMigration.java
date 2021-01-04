package net.ask39.prod_production_standards.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import net.ask39.enums.MyConstants;
import net.ask39.prod_production_standards.entity.*;
import net.ask39.prod_production_standards.service.ProductionStandardsDocRepository;
import net.ask39.utils.JsonUtils;
import net.ask39.utils.MyUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Service
public class ProdProductionStandardsMigration {
    @Resource(name = "askconfigJdbcTemplate")
    private JdbcTemplate askconfigJdbcTemplate;
    @Resource(name = "asklogJdbcTemplate")
    private JdbcTemplate asklogJdbcTemplate;
    @Autowired
    private ProductionStandardsDocRepository productionStandardsDocRepository;

    private static final String STANDARDS_ID_OUT_PUT_FILE_NAME = "data/prod_production_standards_id.txt";
    private static final String STANDARDS_OUT_PUT_FILE_NAME = "data/prod_production_standards.txt";
    private static final String REPLY_STANDARDS_OUT_PUT_FILE_NAME = "data/prod_production_reply_standards.txt";
    private static final String REPLY_STANDARD_MAP_OUT_PUT_FILE_NAME = "data/prod_production_reply_standard_map.txt";
    private OutputStream standardsIdOutPutStream = new FileOutputStream(STANDARDS_ID_OUT_PUT_FILE_NAME);
    private OutputStream standardsOutPutStream = new FileOutputStream(STANDARDS_OUT_PUT_FILE_NAME);
    private OutputStream replyStandardsOutPutStream = new FileOutputStream(REPLY_STANDARDS_OUT_PUT_FILE_NAME);
    private OutputStream standardMapOutPutStream = new FileOutputStream(REPLY_STANDARD_MAP_OUT_PUT_FILE_NAME);

    private long productionStandardsId = 1L;
    private long replyStandardsId = 1L;

    private final Logger log = LoggerFactory.getLogger(ProdProductionStandardsMigration.class);

    public ProdProductionStandardsMigration() throws FileNotFoundException {
    }

    public void migration() throws IOException {
        List<ProductionStandardsDoc> all = productionStandardsDocRepository.findAll();
        all.sort(Comparator.comparing(ProductionStandardsDoc::getCreateOn));
        for (ProductionStandardsDoc productionStandardsDoc : all) {
            log.info("{}", JsonUtils.obj2String(productionStandardsDoc));
            convert(productionStandardsDoc);
        }
        standardsIdOutPutStream.close();
        standardsOutPutStream.close();
        replyStandardsOutPutStream.close();
        standardMapOutPutStream.close();
    }

    private void writerProdProductionStandardsEntity(ProdProductionStandardsEntity standardsEntity) throws IOException {
        List<Object> data = new ArrayList<>();
        data.add(standardsEntity.getId());
        data.add(standardsEntity.getStandardsName());
        data.add(standardsEntity.getDemandPosition());
        data.add(standardsEntity.getDemandPosition());
        data.add(standardsEntity.getNeedDetail());
        data.add(standardsEntity.getFounderName());
        data.add(standardsEntity.getFounderId());
        data.add(standardsEntity.getCreateOn());
        data.add(standardsEntity.getUpdateTime());
        data.add(standardsEntity.getUpdateUser());
        data.add(standardsEntity.getIsDeleted());
        Joiner joiner = Joiner.on(MyConstants.ESC).useForNull("");
        IOUtils.writeLines(Lists.newArrayList(joiner.join(data)), System.getProperty("line.separator"), standardsOutPutStream, MyConstants.CHART_SET);
    }
    private void writerId(Long newId, String oldId) throws IOException {
        Joiner joiner = Joiner.on(MyConstants.ESC);
        IOUtils.writeLines(Lists.newArrayList(joiner.join(Lists.newArrayList(newId, oldId))), System.getProperty("line.separator"), standardsIdOutPutStream, MyConstants.CHART_SET);
    }
    private void writerReplyStandardMap(Long productionStandardsId, Long replyStandardsId, int replyNo) throws IOException {
        Joiner joiner = Joiner.on(MyConstants.ESC);
        IOUtils.writeLines(Lists.newArrayList(joiner.join(Lists.newArrayList(productionStandardsId, replyStandardsId, replyNo))), System.getProperty("line.separator"), standardMapOutPutStream, MyConstants.CHART_SET);
    }
    private void writerReplyStandard(ProdProductionReplyStandardsEntity replyStandardsEntity) throws IOException {
        List<Object> data = new ArrayList<>();
        data.add(replyStandardsEntity.getId());
        data.add(replyStandardsEntity.getProductionStandardsId());
        data.add(replyStandardsEntity.getLowWordCount());
        data.add(replyStandardsEntity.getHighWordCount());
        data.add(replyStandardsEntity.getReplyStandardsDetail());
        data.add(replyStandardsEntity.getTitleDemo());
        data.add(replyStandardsEntity.getContentDemo());
        data.add(replyStandardsEntity.getSchemeContent());
        data.add(replyStandardsEntity.getNeedAuth());
        data.add(replyStandardsEntity.getCreateOn());
        data.add(replyStandardsEntity.getCreateName());
        data.add(replyStandardsEntity.getUpdateTime());
        data.add(replyStandardsEntity.getUpdateName());
        data.add(replyStandardsEntity.getIsDeleted());

        Joiner joiner = Joiner.on(MyConstants.ESC).useForNull("");
        IOUtils.writeLines(Lists.newArrayList(joiner.join(Lists.newArrayList(data))), System.getProperty("line.separator"), replyStandardsOutPutStream, MyConstants.CHART_SET);
    }

    private void convert(ProductionStandardsDoc productionStandards) throws IOException {
        productionStandards = JsonUtils.string2Obj("{\"id\":\"5ef04e6d343a3e9ff5e7fb80\",\"standardsName\":\"ljw-测试标准-1\",\"demandPosition\":3,\"detailStandards\":{\"needDetail\":false},\"replyStandardsList\":[{\"answerStandards\":{\"lowWordCount\":0,\"highWordCount\":300,\"replyStandardsDetail\":\"回复标准-1\",\"titleDemo\":\"题目\",\"contentDemo\":\"内容      1.#######################      2.#######################\"},\"authStandards\":{\"needAuth\":true},\"answerScheme\":{\"keys\":[1,2],\"schemeContent\":[null,\"段落1\",\"段落2\"]}},{\"answerStandards\":{\"lowWordCount\":0,\"highWordCount\":150,\"replyStandardsDetail\":\"修改标准-2\",\"titleDemo\":\"题目\",\"contentDemo\":\"内容\"},\"authStandards\":{\"needAuth\":true}}],\"founderName\":\"root\",\"founderId\":1,\"createOn\":\"2020-06-22 14:27:39\"}", ProductionStandardsDoc.class);
        ProdProductionStandardsEntity dto = new ProdProductionStandardsEntity();
        dto.setId(productionStandardsId++);
        dto.setStandardsName(productionStandards.getStandardsName());
        dto.setDemandPosition(productionStandards.getDemandPosition());
        DetailStandards detailStandards = productionStandards.getDetailStandards();
        dto.setNeedDetail(detailStandards.getNeedDetail() ? 1 : 0);
        dto.setFounderName(productionStandards.getFounderName());
        dto.setFounderId(Long.valueOf(productionStandards.getFounderId()));
        dto.setCreateOn(MyUtils.date2Ldt(productionStandards.getCreateOn()));
        List<ReplyStandards> replyStandardsList = productionStandards.getReplyStandardsList();
        dto.setReplyCount(replyStandardsList.size());
        dto.setUpdateTime(dto.getCreateOn());
        dto.setUpdateUser(dto.getFounderId());
        dto.setIsDeleted(0);

        writerId(dto.getId(), productionStandards.getId());
        writerProdProductionStandardsEntity(dto);

        int replyNo = 1;
        for (ReplyStandards replyStandards : replyStandardsList) {
            ProdProductionReplyStandardsEntity replyStandardsDTO = new ProdProductionReplyStandardsEntity();
            replyStandardsDTO.setId(replyStandardsId++);
            replyStandardsDTO.setProductionStandardsId(dto.getId());
            AnswerStandards answerStandards = replyStandards.getAnswerStandards();
            replyStandardsDTO.setLowWordCount(answerStandards.getLowWordCount());
            replyStandardsDTO.setHighWordCount(answerStandards.getHighWordCount());
            replyStandardsDTO.setReplyStandardsDetail(answerStandards.getReplyStandardsDetail());
            replyStandardsDTO.setTitleDemo(answerStandards.getTitleDemo());
            replyStandardsDTO.setContentDemo(answerStandards.getContentDemo());
            AnswerScheme answerScheme = replyStandards.getAnswerScheme();
            List<String> schemeContent = answerScheme.getSchemeContent();
            if (!CollectionUtils.isEmpty(schemeContent)) {
                replyStandardsDTO.setSchemeContent(Joiner.on(MyConstants.ESC).join(schemeContent.stream().filter(Objects::nonNull).collect(Collectors.toList())));
            }
            AuthStandards authStandards = replyStandards.getAuthStandards();
            replyStandardsDTO.setNeedAuth(authStandards.getNeedAuth() ? 1 : 0);
            replyStandardsDTO.setCreateOn(dto.getCreateOn());
            replyStandardsDTO.setCreateName(dto.getFounderName());
            replyStandardsDTO.setUpdateTime(dto.getUpdateTime());
            replyStandardsDTO.setUpdateName(null);
            replyStandardsDTO.setIsDeleted(dto.getIsDeleted());

            writerReplyStandardMap(dto.getId(), replyStandardsDTO.getId(), replyNo++);
            writerReplyStandard(replyStandardsDTO);
        }
    }
}
