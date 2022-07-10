package com.example.wisdombooks.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wisdombooks.entity.Evaluate;
import com.example.wisdombooks.mapper.EvaluateMapper;
import com.example.wisdombooks.service.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EvaluateServiceImpl extends ServiceImpl<EvaluateMapper, Evaluate> implements EvaluateService {
    @Autowired
    private EvaluateMapper evaluateMapper;

    // 存放迭代找出的所有子代集合
    private List<Evaluate> temp = new ArrayList<>();

    @Override
    public List<Evaluate> listEvaluate() {
        // 查询出父节点
        List<Evaluate> evaluates = evaluateMapper.findByParentIdNull(Long.parseLong("-1"));
        return getEvaluates(evaluates);
    }

//    @Override
//    public List<Evaluate> listEvaluate(String bno) {
//        // 查询出父节点
//        List<Evaluate> evaluates = evaluateMapper.findByParentIdNull(bno);
//        return getEvaluates(evaluates);
//    }

    private List<Evaluate> getEvaluates(List<Evaluate> evaluates) {
        for (Evaluate evaluate : evaluates) {
            Long id = evaluate.getId();
            String parentName1 = evaluate.getStudentName();
            List<Evaluate> evaluateList = evaluateMapper.findParentIdNotNull(id);
            // 查询出子评论
            combineChildren(evaluateList, parentName1);
            evaluate.setReplyEvaluates(temp);
            temp = new ArrayList<>();
        }
        return evaluates;
    }

    private void combineChildren(List<Evaluate> childEvaluates, String parentName1) {
        // 判断是否有一级评论
        if (childEvaluates.size() > 0) {
            for (Evaluate childEvaluate : childEvaluates) {
                String parentName = childEvaluate.getStudentName();
                childEvaluate.setParentStudentName(parentName1);
                temp.add(childEvaluate);
                Long childId = childEvaluate.getId();
                // 查询二级以及所有子级回复
                recursively(childId, parentName);
            }
        }
    }

    private void recursively(Long childId, String parentName1) {
        // 根据子一级评论的id找到二级评论
        List<Evaluate> replayEvaluates = evaluateMapper.findByReplayId(childId);
        if (replayEvaluates.size() > 0) {
            for (Evaluate replayEvaluate : replayEvaluates) {
                String parentName = replayEvaluate.getStudentName();
                replayEvaluate.setParentStudentName(parentName1);
                Long rep = replayEvaluate.getId();
                temp.add(replayEvaluate);
                // 递归迭代找出子级回复
                recursively(rep, parentName);
            }
        }
    }
}
