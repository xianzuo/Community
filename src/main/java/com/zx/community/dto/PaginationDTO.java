package com.zx.community.dto;

import com.zx.community.model.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;//当前页
    private List<Integer> pages=new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        Integer totalPage;
        this.page=page;
        if(totalCount%size==0){
            totalPage=totalCount/size;
        }
        else {
            totalPage=totalCount/size+1;
        }
        this.totalPage=totalPage;
        Integer first=1,end=totalPage;
        if(page-first>=3){
            first=page-3;
            if(end-page>=3){
                end=page+3;
            }
        }
        else{
            if(end>=7)end=7;
        }

        for(int i=first;i<=end;i++){
            pages.add(i);
        }
        //是否展示上一页
        if(page==1){
            showPrevious=false;
        }
        else {
            showPrevious=true;
        }
        //是否展示下一页
        if(totalPage==page){
            showNext=false;
        }
        else {
            showNext=true;
        }
        //是否展示首页
        if(pages.contains(1)){
            showFirstPage=false;
        }
        else {
            showFirstPage=true;
        }
        //是否展示尾页
        if(pages.contains(totalPage)){
            showEndPage=false;
        }else{
            showEndPage=true;
        }
    }
}
