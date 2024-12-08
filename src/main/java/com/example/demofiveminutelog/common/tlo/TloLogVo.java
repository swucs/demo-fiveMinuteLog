package com.example.demofiveminutelog.common.tlo;


import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Data
public class TloLogVo implements Serializable {

	public static final String LOG_TYPE = "SVC";
	public static final String DB_SUCCESS_CODE = "SUCCESS";
	public static final String DB_FAIL_CODE = "FAIL";
	public static final String SVC_NAME = "GM";
	public static final String SCN_NAME = "DEMO-FiveMinuteLog";

	private String seqId;   	// len :25 로그 단위 Unique한 ID
	private String logTime; 	// len :14 로그를 파일에 Write 시점 시간
	private String logType; 	// len :3 SVC 하드코딩.
    private String sid;     	// len :50 TLO 규격의 분류 코드
	private String resultCode; 	// len :8 서비스 상태코드 (성공, 실패,구간실패)
	private String reqTime;   	// len :17 사용자의 요청 발생 시간
	private String rspTime;   	// len :17 사용자의 응답 발생 시간
    private String clientIp;  	// len: 40 접속 클라이언트 IP
    private String devInfo;   	// len: 5 접속 단말 타입
    private String osInfo;    	// len: 50 OS정보
    private String nwInfo;	  	// len: 5 접속 네트워크 정보
    private String svcName;   	// len: 32 각 서비스/시스템 명(통계사전 참조)
    private String devModel;  	// len: 50  단말 모델명. ncas 전달받은 내용으로 채움.
    private String carrierType; // len: 1  통신사 구분 . 우선 L으로 한다.
	private String svcClass; 	// len: 10 분류항목(API명)
	private String hostName; 	// len: 32 서버 호스트명
	private String scnName; 	// len: 32 시나리오이름(Service명)
	private String dbResultCode;	//Aurora DB 연동 결과 코드
	private String dbReqTime;		//Aurora DB 요청시간
	private String dbRspTime;		//Aurora DB 응답시간



	public void setSeqId() {
		String seq_date = getNowString();
		this.seqId = seq_date + UUID.randomUUID().toString().substring(0, 4) + UUID.randomUUID().toString().substring(0, 4);
	}

	public void setLogTime() {
		this.logTime = getNowString();
	}

	public void setReqTime() {
		this.reqTime = getNowString();
	}

	public void setRspTime() {
		this.rspTime = getNowString();
	}

	public void setDbReqTime() {
		this.dbReqTime = getNowString();
	}

	public void setDbRspTime() {
		this.dbRspTime = getNowString();
	}

	@Override
	public String toString() {
		return "SEQ_ID=" + StringUtils.defaultString(seqId) +
				"|LOG_TIME=" + StringUtils.defaultString(logTime) +
				"|LOG_TYPE=" + StringUtils.defaultString(logType) +
				"|SID=" + StringUtils.defaultString(sid) +
				"|RESULT_CODE=" + StringUtils.defaultString(resultCode) +
				"|REQ_TIME=" + StringUtils.defaultString(reqTime) +
				"|RSP_TIME=" + StringUtils.defaultString(rspTime) +
				"|CLIENT_IP=" + StringUtils.defaultString(clientIp) +
				"|DEV_INFO=" + StringUtils.defaultString(devInfo) +
				"|OS_INFO=" + StringUtils.defaultString(osInfo) +
				"|NW_INFO=" + StringUtils.defaultString(nwInfo) +
				"|SVC_NAME=" + StringUtils.defaultString(svcName) +
				"|DEV_MODEL=" + StringUtils.defaultString(devModel) +
				"|CARRIER_TYPE=" + StringUtils.defaultString(carrierType) +
				"|SVC_CLASS=" + StringUtils.defaultString(svcClass) +
				"|HOST_NAME=" + StringUtils.defaultString(hostName) +
				"|SCN_NAME=" + StringUtils.defaultString(scnName) +
				"|DB_RESULT_CODE=" + StringUtils.defaultString(dbResultCode) +
				"|DB_REQ_TIME=" + StringUtils.defaultString(dbReqTime) +
				"|DB_RSP_TIME=" + StringUtils.defaultString(dbRspTime)
				;
	}
	

	private String getNowString() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
	}
}
