package edu.taesu.myspring;

import java.io.Serializable;

public class ResultVO implements Serializable {

	private String resultCd;
	private String resultMsg;
	private String resultFlag;

    public void setResultCd(String resultCd) {
        this.resultCd = resultCd;
    }

    public String getResultCd() {
        return resultCd;
    }

    public void setResultFlag(String resultFlag) {
        this.resultFlag = resultFlag;
    }

    public String getResultFlag() {
        return resultFlag;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getResultMsg() {
        return resultMsg;
    }
}
