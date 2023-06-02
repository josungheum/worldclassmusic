package qss.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.KioskResourceVo;
import qss.vo.LayerContentVo;
import qss.vo.MainScheduleVo;
import qss.vo.OptionProdVo;
import qss.vo.OrderItemVo;
import qss.vo.OrderMasterVo;
import qss.vo.OrderProdVo;
import qss.vo.OrderScreenAPIVo;
import qss.vo.OrderScreenMenuItemVo;
import qss.vo.OrderScreenMenuVo;
import qss.vo.OrderScreenResultVo;
import qss.vo.RestAPIVo;
import qss.vo.ScheduleScreenVo;
import qss.vo.ScreenLayerVo;
import qss.vo.ScreenResultVo;
import qss.vo.UploadVo;
import qss.vo.ControlScheduleVo;


@Repository("restAPIDao")
public class RestAPIDao extends EgovAbstractMapper{


	@Autowired private PlatformTransactionManager transactionManager;



	/**
	 * <pre>
	 * 1. 개요 : 조회 관련
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : SelectData
	 * @date : 2019. 1. 23.
	 * @author : admin
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 23.		admin				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param restAPIVo
	 * @return
	 */
	public RestAPIVo SelectData(RestAPIVo restAPIVo) {
		ObjectMapper mapper = new ObjectMapper();
		RestAPIVo returnVo = new RestAPIVo();
		String castStr = restAPIVo.getCaseString();
		if(castStr.equals("GetBrandDetail")
				|| castStr.equals("GetFranchiseeDetail")
				|| castStr.equals("GetDeviceDetail")
				|| castStr.equals("GetNoticeList")
				|| castStr.equals("GetSoftwareUpdateList")
				|| castStr.equals("GetDocNo")
				|| castStr.equals("GetEventContentList")
				|| castStr.equals("GetOrderList")
		) {
			returnVo = getDetail(restAPIVo, mapper);
		}
		else if(castStr.equals("UpdateProductList")){
			returnVo = getProdUpdate(restAPIVo, mapper);
		}
		else if(castStr.equals("UpdateResourceDetail")) {
			returnVo = getResourceUpdate(restAPIVo, mapper);
		}else if(castStr.equals("InsertOrderDetail")) {
			returnVo = InsertOrderDetail(restAPIVo, mapper);
		}else if(castStr.equals("UpdateOrderDetail")) {
			returnVo = UpdateOrderDetail(restAPIVo, mapper);
		}else if(castStr.equals("GetProductList")) {
			returnVo = GetProductList(restAPIVo, mapper);	
		}else if(castStr.equals("GetScreenScheduleList")) {
			returnVo = GetScreenScheduleList(restAPIVo, mapper);
		}else if(castStr.equals("GetMenuList")) {
			returnVo = GetMenuList(restAPIVo, mapper);
		}

		return returnVo;
	}

	/**
	 * <pre>
	 * 1. 개요 : 리소스 등록/수정
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : getResourceUpdate
	 * @date : 2019. 1. 23.
	 * @author : admin
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 23.		admin				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param restAPIVo
	 * @param mapper
	 * @return
	 */
	private RestAPIVo getResourceUpdate(RestAPIVo restAPIVo, ObjectMapper mapper) {
		RestAPIVo returnRestAPIVo = new  RestAPIVo();
		String errorMessage = "";
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
	    TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
		//유효성 체크
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        try {
        	for(int i = 0; i < restAPIVo.getKioskResourceList().size(); i++) {
            	KioskResourceVo kioskResourceVo = restAPIVo.getKioskResourceList().get(i);
            	Set<ConstraintViolation<KioskResourceVo>> constraintViolations = validator.validate(kioskResourceVo);
                if (constraintViolations.size() > 0) {
                    for (ConstraintViolation<KioskResourceVo> violation : constraintViolations) {
                        errorMessage += (i+1)+" row:"+ violation.getMessage() ;
                    }
                    returnRestAPIVo.setErrorCode("1");
                }
            }

        } catch (Exception e) {
        	returnRestAPIVo.setErrorCode("1");
        	errorMessage = "유효성 체크에서 오류가 발생하였습니다.";
		}

        if(errorMessage.equals("")) {
        	//수정인지 저장인지 체크
        	int insertCnt = 0;
    		int updateCnt = 0;
    		try {
    			for(int i = 0; i < restAPIVo.getKioskResourceList().size(); i++) {
            		KioskResourceVo kioskResourceVo = restAPIVo.getKioskResourceList().get(i);
            		HashMap<String, Object> idxMap = selectOne("RestAPI_IdxSearch",kioskResourceVo.getDeviceCode());
            		if(idxMap != null) {
            			kioskResourceVo.setDomainIdx(idxMap.get("DOMAIN_IDX").toString());
            			kioskResourceVo.setBrandIdx(idxMap.get("BRAND_IDX").toString());
            			kioskResourceVo.setFrancIdx(idxMap.get("FRANC_IDX").toString());
            			kioskResourceVo.setDeviceIdx(BigInteger.valueOf(Long.parseLong(idxMap.get("DEVICE_IDX").toString())));
            			int resourceCnt = selectOne("RestAPI_OverlapCountKioskResource",kioskResourceVo);

                    	//수정
                    	if(resourceCnt > 0) {

                    		// 단말 접속시작시간 update 필요한지 체크
                    		kioskResourceVo.setConnectStartTimeYn(selectOne("RestAPI_SelectKioskResource",kioskResourceVo));

                    		updateCnt += update("RestAPI_UpdateKioskResource", kioskResourceVo);
                    	//저장
                    	}else {
                    		insertCnt += update("RestAPI_InsertKioskResource", kioskResourceVo);
                    	}
                    	
                    	//20200413 kiosk 리소스 상태 업데이트 시 버전정보 같이 업데이트 
                    	//버전 정보를 리소스에 추가(VERSION)하는 것으로 하여 주석처리
                        /*int lastestVersionCnt = selectOne("RestAPI_OverlapCountKioskVersion",kioskResourceVo);
                        
                        if(lastestVersionCnt == 0) {
                            update("RestAPI_InsertKioskLastestVersion", kioskResourceVo);
                        }*/

                    	returnRestAPIVo.setErrorCode("0");
                    	returnRestAPIVo.setRetData(insertCnt+"건 저장, "+updateCnt+"건 수정 되었습니다.");
                    	returnRestAPIVo.setRetObjectData(insertCnt+"건 저장, "+updateCnt+"건 수정 되었습니다.");
            		}else {
            			returnRestAPIVo.setErrorCode("1");
            			errorMessage = "단말이 존재하지 않습니다.";
            		}
            	}
    			transactionManager.commit(transactionStatus);
    		} catch (Exception e) {
    			System.out.println(e);
    			transactionManager.rollback(transactionStatus);
    			returnRestAPIVo.setErrorCode("1");
    			errorMessage = "데이터베이스 오류가 발생하였습니다.";
			}

        }
        returnRestAPIVo.setErrorMessage(errorMessage);
    	return returnRestAPIVo;
	}

	/**
	 * <pre>
	 * 1. 개요 : 상품 등록/수정
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : getProdUpdate
	 * @date : 2019. 1. 23.
	 * @author : admin
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 23.		admin				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param restAPIVo
	 * @param mapper
	 * @return
	 */
	private RestAPIVo getProdUpdate(RestAPIVo restAPIVo, ObjectMapper mapper) {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
	    TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
		RestAPIVo returnRestAPIVo = new  RestAPIVo();
		String errorMessage = "";
		//유효성 체크
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        try {
        	for(int i = 0; i < restAPIVo.getOrderProdList().size(); i++) {
            	OrderProdVo orderProdVo = restAPIVo.getOrderProdList().get(i);
            	Set<ConstraintViolation<OrderProdVo>> constraintViolations = validator.validate(orderProdVo);
                if (constraintViolations.size() > 0) {
                    for (ConstraintViolation<OrderProdVo> violation : constraintViolations) {
                        errorMessage += (i+1)+" row:"+ violation.getMessage() ;
                    }
                    returnRestAPIVo.setErrorCode("1");
                }
                // 주문상품 유효성 체크
                for(int j = 0; j < orderProdVo.getOptionProdList().size(); j++) {
                	OptionProdVo optionProdVo = orderProdVo.getOptionProdList().get(j);
                	Set<ConstraintViolation<OptionProdVo>> constraintViolationsItem = validator.validate(optionProdVo);
                    if (constraintViolationsItem.size() > 0) {
                        for (ConstraintViolation<OptionProdVo> violation : constraintViolationsItem) {
                            errorMessage += (j+1)+" row:"+ violation.getMessage() ;
                        }
                        returnRestAPIVo.setErrorCode("1");
                        break;
                    }
                }
            }
        } catch (Exception e) {
        	returnRestAPIVo.setErrorCode("1");
        	errorMessage = "유효성 체크에서 오류가 발생하였습니다.";
		}

        if(errorMessage.equals("")) {
        	int insertCnt = 0;
    		int updateCnt = 0;
        	try {

    			for(int i = 0; i < restAPIVo.getOrderProdList().size(); i++) {
	        		OrderProdVo orderProdVo = restAPIVo.getOrderProdList().get(i);
	        		//단말 코드로 domain, brand, franc, device 일련번호 조회

	    			HashMap<String, Object> idxMap = selectOne("RestAPI_IdxSearch",orderProdVo.getDeviceCode());
	        		if(idxMap != null) {
	        			orderProdVo.setDomainIdx(idxMap.get("DOMAIN_IDX").toString());
	            		orderProdVo.setBrandIdx(idxMap.get("BRAND_IDX").toString());
	            		orderProdVo.setFrancIdx(idxMap.get("FRANC_IDX").toString());
	            		orderProdVo.setDeviceIdx(BigInteger.valueOf(Long.parseLong(idxMap.get("DEVICE_IDX").toString())));
	        		}
	        		//수정인지 저장인지 체크

	        		BigInteger orderProdIdx = selectOne("RestAPI_OverlapCountOrderProd",orderProdVo);

	            	//수정
	            	if(orderProdIdx != null) {
	            		orderProdVo.setOrderProdIdx(orderProdIdx);
	            		updateCnt += update("RestAPI_UpdateOrderProd", orderProdVo);
	            	//저장
	            	}else {
	            		insertCnt += update("RestAPI_InsertOrderProd", orderProdVo);
	            		orderProdVo.setOrderProdIdx(orderProdVo.getReturnIdx());
	            	}
	            	
	            	//옵션 상품 삭제
	            	delete("OrderProd_DeleteOrderProdOptionTarget", orderProdVo);

	    			// 신규 스크린 정보 처리
	    			/*스케줄 적용 스크린 리스트 insert*/
	            	OrderProdVo newOrderProdVo = new OrderProdVo();
	    			if(orderProdVo.getOptionProdList() != null) {
	    				for(int j = 0; j < orderProdVo.getOptionProdList().size(); j++) {
	    					
	    					// 리스트에서 해당 상품IDX 별로 설정
	    					String optionProdCode = orderProdVo.getOptionProdList().get(j).getOptionProdCode();
	    					newOrderProdVo.setDomainIdx(orderProdVo.getDomainIdx());
	    					newOrderProdVo.setBrandIdx(orderProdVo.getBrandIdx());
	    					newOrderProdVo.setFrancIdx(orderProdVo.getFrancIdx());
	    					newOrderProdVo.setOrderProdIdx(orderProdVo.getOrderProdIdx());
	    					newOrderProdVo.setOptionProdCode(optionProdCode);
	    					newOrderProdVo.setOptionProdIdx(selectOne("RestAPI_OptionCodeOptionIdx",newOrderProdVo));
	    					newOrderProdVo.setOrderSeq(j+1);
	    					newOrderProdVo.setRegUser("admin");
	    					insert("OrderProd_InsertOrderProdOptionTarget", newOrderProdVo);
	    				}
	    			}
	            	System.out.println(orderProdVo);
	            	//옵션 상품 추가
	            
	        	}
    			transactionManager.commit(transactionStatus);
    		}catch (Exception e) {
    			transactionManager.rollback(transactionStatus);
    			returnRestAPIVo.setErrorCode("1");
    			errorMessage = "데이터베이스 오류가 발생하였습니다.";
			}
        	returnRestAPIVo.setErrorCode("0");
        	returnRestAPIVo.setRetData(insertCnt+"건 저장, "+updateCnt+"건 수정 되었습니다.");
        	returnRestAPIVo.setRetObjectData(insertCnt+"건 저장, "+updateCnt+"건 수정 되었습니다.");

        }
        returnRestAPIVo.setErrorMessage(errorMessage);
    	return returnRestAPIVo;
    }

	public int InsertData(RestAPIVo restAPIVo) throws Exception {

		return 0;
	}


	public List<?> SelectListData(RestAPIVo restAPIVo) {

		return null;
	}

	public String SelectByReturnString(RestAPIVo restAPIVo) {
		return null;
	}

	public HashMap SelectDataMap(RestAPIVo restAPIVo) {
		return null;
	}

	public void insertOrderHistory(OrderMasterVo orderMasterVo) {
		// 주문 마스터 히스토리 등록
		/*OrderHistoryVo orderHistoryVo = new OrderHistoryVo();
		orderHistoryVo.setDomainIdx(orderMasterVo.getDomainIdx());
		orderHistoryVo.setBrandIdx(orderMasterVo.getBrandIdx());
		orderHistoryVo.setFrancIdx(orderMasterVo.getFrancIdx());
		orderHistoryVo.setDeviceIdx(orderMasterVo.getDeviceIdx());
		orderHistoryVo.setOrderMasterIdx(orderMasterVo.getOrderMasterIdx());
		orderHistoryVo.setReceiptNo(orderMasterVo.getReceiptNo());
		orderHistoryVo.setOrderDate(orderMasterVo.getOrderDate());
		orderHistoryVo.setOrderTime(orderMasterVo.getOrderTime());
		orderHistoryVo.setOrderAmt(orderMasterVo.getOrderAmt());
		orderHistoryVo.setDiscAmt(orderMasterVo.getDiscAmt());
		orderHistoryVo.setPayAmt(orderMasterVo.getPayAmt());
		orderHistoryVo.setPayType(orderMasterVo.getPayType());
		orderHistoryVo.setPayProcCode(orderMasterVo.getPayProcCode());
		orderHistoryVo.setCardAmt(orderMasterVo.getCardAmt());
		orderHistoryVo.setPointTypeCode(orderMasterVo.getPointTypeCode());
		orderHistoryVo.setPointUseAmt(orderMasterVo.getPointUseAmt());
		orderHistoryVo.setPointSaveAmt(orderMasterVo.getPointSaveAmt());
		orderHistoryVo.setCalculateDate(orderMasterVo.getCalculateDate());
		orderHistoryVo.setCalculateYn(orderMasterVo.getCalculateYn());
		orderHistoryVo.setRegUser(orderMasterVo.getRegUser());
		orderHistoryVo.setRegDate(orderMasterVo.getRegDate());
		orderHistoryVo.setModUser(orderMasterVo.getModUser());
		orderHistoryVo.setModDate(orderMasterVo.getModDate());
		orderHistoryVo.setDeviceCode(orderMasterVo.getDeviceCode());
		insert("InsertData_InsertOrderMasterHistory", orderHistoryVo);*/
	}

	/**
	 * <pre>
	 * 1. 개요 : 주문등록
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : InsertOrderDetail
	 * @date : 2019. 1. 23.
	 * @author : ksh
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 23.		ksh				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param restAPIVo
	 * @param mapper
	 * @return
	 */
	private RestAPIVo InsertOrderDetail(RestAPIVo restAPIVo, ObjectMapper mapper) {
		RestAPIVo returnRestAPIVo = new  RestAPIVo();
		String errorMessage = "";
		try {
			//유효성 체크
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	        Validator validator = factory.getValidator();
	        for(int i = 0; i < restAPIVo.getOrderMasterList().size(); i++) {
	        	OrderMasterVo orderMasterVo = restAPIVo.getOrderMasterList().get(i);
	        	Set<ConstraintViolation<OrderMasterVo>> constraintViolations = validator.validate(orderMasterVo);
	            if (constraintViolations.size() > 0) {
	                for (ConstraintViolation<OrderMasterVo> violation : constraintViolations) {
	                    errorMessage += (i+1)+" row:"+ violation.getMessage() ;
	                }
	                returnRestAPIVo.setErrorCode("1");
	            }
	        }
		} catch (Exception e) {
	    	returnRestAPIVo.setErrorCode("1");
	    	errorMessage = "유효성 체크에서 오류가 발생하였습니다.";
		}

		try {
			 if(errorMessage.equals("")) {
	        	//수정인지 저장인지 체크
	        	OrderMasterVo newOrderMasterVo = new OrderMasterVo();
	        	for(int i = 0; i < restAPIVo.getOrderMasterList().size(); i++) {
	        		OrderMasterVo orderMasterVo = restAPIVo.getOrderMasterList().get(i);

	        		insert("InsertData_InsertOrderMaster", orderMasterVo);
	        		newOrderMasterVo = orderMasterVo;

	        		// 주문 마스터 히스토리 등록
	        		insertOrderHistory(orderMasterVo);
	        	}
	        	returnRestAPIVo.setErrorCode("0");
	        	String jsonList = mapper.writeValueAsString(newOrderMasterVo);
				returnRestAPIVo.setRetData(jsonList);
				returnRestAPIVo.setRetObjectData(newOrderMasterVo);
	        }
		}catch (Exception e) {
			returnRestAPIVo.setErrorCode("1");
			errorMessage = "데이터베이스 오류가 발생하였습니다.";
		}

        returnRestAPIVo.setErrorMessage(errorMessage);
    	return returnRestAPIVo;
    }

	/**
	 * <pre>
	 * 1. 개요 : 주문정보 update
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : UpdateOrderDetail
	 * @date : 2019. 1. 23.
	 * @author : ksh
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 23.		ksh				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param restAPIVo
	 * @param mapper
	 * @return
	 */
	private RestAPIVo UpdateOrderDetail(RestAPIVo restAPIVo, ObjectMapper mapper) {
		RestAPIVo returnRestAPIVo = new  RestAPIVo();
		String errorMessage = "";
		//유효성 체크
		try {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	        Validator validator = factory.getValidator();
	        for(int i = 0; i < restAPIVo.getOrderMasterList().size(); i++) {
	        	OrderMasterVo orderMasterVo = restAPIVo.getOrderMasterList().get(i);
	        	Set<ConstraintViolation<OrderMasterVo>> constraintViolations = validator.validate(orderMasterVo);
	            if (constraintViolations.size() > 0) {
	                for (ConstraintViolation<OrderMasterVo> violation : constraintViolations) {
	                    errorMessage += (i+1)+" row:"+ violation.getMessage() ;
	                }
	                returnRestAPIVo.setErrorCode("1");
	                break;
	            }

	            // 주문상품 유효성 체크
	            for(int j = 0; j < orderMasterVo.getOrderItemVo().size(); j++) {
	            	OrderItemVo orderItemVo = orderMasterVo.getOrderItemVo().get(j);
	            	Set<ConstraintViolation<OrderItemVo>> constraintViolationsItem = validator.validate(orderItemVo);
	                if (constraintViolationsItem.size() > 0) {
	                    for (ConstraintViolation<OrderItemVo> violation : constraintViolationsItem) {
	                        errorMessage += (i+1)+" row:"+ violation.getMessage() ;
	                    }
	                    returnRestAPIVo.setErrorCode("1");
	                    break;
	                }
	            }
	        }
		} catch (Exception e) {
	    	returnRestAPIVo.setErrorCode("1");
	    	errorMessage = "유효성 체크에서 오류가 발생하였습니다.";
		}

		try {

	        if(errorMessage.equals("")) {
	        	String returnReceiptNo = "";
	        	for(int i = 0; i < restAPIVo.getOrderMasterList().size(); i++) {
	        		OrderMasterVo orderMasterVo = restAPIVo.getOrderMasterList().get(i);

	        		// 주문 마스터 UPDATE
	        		update("InsertData_UpdateOrderDetail", orderMasterVo);

	        		// 주문 마스터 히스토리 등록
	        		insertOrderHistory(orderMasterVo);

	        		if(orderMasterVo.getOrderItemVo().size() > 0) {

	        			// 기존 주문 상품을 삭제 헌다.
		        		delete("InsertData_DeleteOrderItemDetail", orderMasterVo);

		        		// 삭제 후 새로 상품 insert
	//	        		insert("InsertData_UpdateOrderItemDetail", orderMasterVo.getOrderItemVo());
		    			for(int j = 0; j < orderMasterVo.getOrderItemVo().size(); j++) {
		    				insert("InsertData_UpdateOrderItemDetail", orderMasterVo.getOrderItemVo().get(j));
		    			}
	        		}
	        	}
	        	returnRestAPIVo.setErrorCode("0");
	        	returnRestAPIVo.setRetData(returnReceiptNo);
	        	returnRestAPIVo.setRetObjectData(returnReceiptNo);
	        }

		}catch (Exception e) {
			returnRestAPIVo.setErrorCode("1");
			errorMessage = "데이터베이스 오류가 발생하였습니다.";
		}
		returnRestAPIVo.setErrorMessage(errorMessage);
    	return returnRestAPIVo;
    }



	//조회 관련
	private RestAPIVo getDetail(RestAPIVo restAPIVo, ObjectMapper mapper) {
		RestAPIVo returnRestAPIVo = new  RestAPIVo();
		String errorMessage = "";
		//유효성 체크
		try {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	        Validator validator = factory.getValidator();
	        Set<ConstraintViolation<RestAPIVo>> constraintViolations = validator.validate(restAPIVo);
	        if (constraintViolations.size() > 0) {
	            for (ConstraintViolation<RestAPIVo> violation : constraintViolations) {
	                errorMessage += violation.getMessage() ;
	            }
	            restAPIVo.setErrorCode("1");
	        } else {
	        	try {
	        		HashMap<String, Object> idxMap = new HashMap<String, Object>();
	        		idxMap = selectOne("RestAPI_IdxSearch",restAPIVo.getDeviceCode());
	        		if(idxMap != null) {
	        			restAPIVo.setDomainIdx(idxMap.get("DOMAIN_IDX").toString());
        				restAPIVo.setBrandIdx(idxMap.get("BRAND_IDX").toString());
        				restAPIVo.setFrancIdx(idxMap.get("FRANC_IDX").toString());
        				restAPIVo.setDeviceIdx(BigInteger.valueOf(Long.parseLong(idxMap.get("DEVICE_IDX").toString())));
        				
	        			List<?> list = selectList(restAPIVo.getCaseString(), restAPIVo);
		    			//최종버전
		    			if(restAPIVo.getCaseString().equals("GetSoftwareUpdateList")) {
		    				returnRestAPIVo.setLastClientVersion(selectOne("GetLastSoftVersion",restAPIVo));
		    			}else {
		    				returnRestAPIVo.setLastVersion(selectOne("GetLastVersion",restAPIVo));
		    			}

		    			if(list.size() == 0) {
		    				errorMessage = "데이터가 한건도 없습니다.";
		    				returnRestAPIVo.setErrorCode("0");
		    			}else {
		    				String jsonList = mapper.writeValueAsString(list);
		    				returnRestAPIVo.setRetData(jsonList);
		    				returnRestAPIVo.setRetObjectData(list);
		    				returnRestAPIVo.setErrorCode("0");
		    			}
	        		}else {
	        			returnRestAPIVo.setErrorCode("1");
	        			errorMessage = "단말이 존재하지 않습니다.";

	        		}


	    		} catch (JsonProcessingException e) {
	    			returnRestAPIVo.setErrorCode("1");
	    			errorMessage = "데이터베이스 오류가 발생하였습니다.";
	    		}

	        }
		} catch (Exception e) {
			returnRestAPIVo.setErrorCode("1");
			errorMessage = "서버오류가 발생하였습니다.";
		}

		returnRestAPIVo.setErrorMessage(errorMessage);

		return returnRestAPIVo;
	}

	/**
	 * <pre>
	 * 1. 개요 : 재생 스케줄 목록 조회
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : GetScreenScheduleList
	 * @date : 2019. 1. 23.
	 * @author : ksh
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 23.		ksh				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param restAPIVo
	 * @param mapper
	 * @return
	 */
	private RestAPIVo GetScreenScheduleList(RestAPIVo restAPIVo, ObjectMapper mapper) {
		RestAPIVo returnRestAPIVo = new  RestAPIVo();
		String errorMessage = "";
		//유효성 체크
		try {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	        Validator validator = factory.getValidator();
	        Set<ConstraintViolation<RestAPIVo>> constraintViolations = validator.validate(restAPIVo);
	        if (constraintViolations.size() > 0) {
	            for (ConstraintViolation<RestAPIVo> violation : constraintViolations) {
	                errorMessage += violation.getMessage() ;
	            }
	            restAPIVo.setErrorCode("1");
	        } else {
	        	try {
	        		List<HashMap<String, Object>> fileContentlist = new ArrayList<HashMap<String, Object>>();			// 파일컨텐츠 idx 리스트
	        		HashMap<String, Object> params = new HashMap<String, Object>();
	        		HashMap<String, Object> fileParams = new HashMap<String, Object>();
	        		ScreenResultVo resulVo = new ScreenResultVo();
	        		// 메인스케줄 정보
	        		List<MainScheduleVo> mainList = selectList("GetMainSchedule", restAPIVo);
	        		List<ControlScheduleVo> controlList = selectList("GetControlSchedule", restAPIVo);
	        		List<ScheduleScreenVo> screenlist = new ArrayList<ScheduleScreenVo>();	// 스크린 리스트
	        		List<ScreenLayerVo> Layerlist = new ArrayList<ScreenLayerVo>();			// 레이어 리스트
	        		List<LayerContentVo> conentlist = new ArrayList<LayerContentVo>();		// 컨텐츠 리스트
	        		List<UploadVo> filelist = new ArrayList<UploadVo>();					// 파일 리스트

	        		//최종버전
	    			returnRestAPIVo.setLastVersion(selectOne("GetLastVersion",restAPIVo));

	        		// 메인스케줄에 하위 자식들 [메인스케줄:스크린(1:N)]
	        		for(int mainCnt=0; mainCnt<mainList.size(); mainCnt++) {
	        			MainScheduleVo mainResultVo = mainList.get(mainCnt);
	        			params.put("mainScheduleIdx", mainResultVo.getMainScheduleIdx());
	        			params.put("deviceIdx", mainResultVo.getDeviceIdx());
	        			params.put("domainIdx", mainResultVo.getDomainIdx());
	        			params.put("brandIdx", mainResultVo.getBrandIdx());
	        			params.put("francIdx", mainResultVo.getFrancIdx());

	        		
        				// 스크린 리스트[스크린:레이어(1:N)]
	        			screenlist = new ArrayList<ScheduleScreenVo>();
	        			screenlist = selectList("GetSreen", params);
	        			for(int screenCnt=0; screenCnt<screenlist.size(); screenCnt++) {
	        				ScheduleScreenVo screenResultVo = screenlist.get(screenCnt);
		        			params.put("screenIdx", screenResultVo.getScreenIdx());
		        			
		        			if(screenResultVo.getScreenType().equals("S"))
		        			{
		        				// 레이어 리스트[레이어:컨텐트(1:N)]
			        			Layerlist = new ArrayList<ScreenLayerVo>();
			        			Layerlist = selectList("GetSreenLayer", params);
			        			if(Layerlist.size() > 0) {
			        				for(int layerCnt=0; layerCnt<Layerlist.size(); layerCnt++) {
				        				ScreenLayerVo layerResultVo = Layerlist.get(layerCnt);

				        				params.put("screenLayerIdx", layerResultVo.getScreenLayerIdx());
					        			// 컨텐트 리스트
				        				conentlist = new ArrayList<LayerContentVo>();
					        			conentlist = selectList("GetLayerContent", params);

					        			for(int cnt=0; cnt<conentlist.size(); cnt++) {
					        				fileParams = new HashMap<String, Object>();
					        				fileParams.put("fileContentIdx", conentlist.get(cnt).getFileContentIdx());
					        				fileParams.put("realPath", restAPIVo.getRealPath());
					        				fileContentlist.add(fileParams);
					        			}

					        			// 해당 레이어에 속한 컨텐츠 리스트 set
					        			layerResultVo.setLayerContentVoList(conentlist);
				        			}
			        			}

			        			// 스크린에 레이어 set
			        			screenResultVo.setScreenLayerVoList(Layerlist);
		        			}
	        			else if(screenResultVo.getScreenType().equals("C")){
	                          fileParams = new HashMap<String, Object>();
	                          fileParams.put("fileContentIdx", screenResultVo.getScreenIdx());
	                          fileParams.put("realPath", restAPIVo.getRealPath());
	                          fileContentlist.add(fileParams);
	                       }
	        			}

	        			// 메인스케줄에 스크린 set
	        			mainResultVo.setScheduleScreenVoList(screenlist);
        			}
        			
	        		if(fileContentlist != null && fileContentlist.size() > 0) {
	        			filelist = selectList("GetFileContent", fileContentlist);
	        		}

        			resulVo.setMainScheduleVoList(mainList);
        			resulVo.setControlScheduleVoList(controlList);
        			resulVo.setUploadVoList(filelist);
	        		
        			//2020.04.06 : 스크린 스케줄이 없을 때 제어스케줄이 있지만 내려가지 않는 문제를 해결
	    			//if(mainList.size() == 0) {
	    			//	errorMessage = "데이터가 한건도 없습니다.";
	    			//	returnRestAPIVo.setErrorCode("0");
	    			//}else {
	    				String jsonList = mapper.writeValueAsString(resulVo);
	    				returnRestAPIVo.setRetData(jsonList);
	    				returnRestAPIVo.setRetObjectData(resulVo);
	    				returnRestAPIVo.setErrorCode("0");
	    			//}

	    		} catch (JsonProcessingException e) {
	    			returnRestAPIVo.setErrorCode("1");
	    			errorMessage = "데이터베이스 오류가 발생하였습니다.";
	    		}

	        }
		} catch (Exception e) {
			returnRestAPIVo.setErrorCode("1");
			errorMessage = "서버오류가 발생하였습니다.";
		}

		returnRestAPIVo.setErrorMessage(errorMessage);

		return returnRestAPIVo;
    }

	/**
	 * 메뉴 조회
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : GetMenuList
	 * @date : 2019. 6. 17.
	 * @author : 김한기
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 17.		김한기				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param restAPIVo
	 * @param mapper
	 * @return
	 */
	private RestAPIVo GetMenuList(RestAPIVo restAPIVo, ObjectMapper mapper) {
		RestAPIVo returnRestAPIVo = new  RestAPIVo();
		OrderScreenResultVo resulVo = new OrderScreenResultVo();
		String errorMessage = "";
		//유효성 체크
		try {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	        Validator validator = factory.getValidator();
	        Set<ConstraintViolation<RestAPIVo>> constraintViolations = validator.validate(restAPIVo);
	        if (constraintViolations.size() > 0) {
	            for (ConstraintViolation<RestAPIVo> violation : constraintViolations) {
	                errorMessage += violation.getMessage() ;
	            }
	            restAPIVo.setErrorCode("1");
	        } else {
	        	try {
	        		List<HashMap<String, Object>> orderScreenList = selectList("GetOrderScreenList", restAPIVo);
	        		List<HashMap<String, Object>> orderScreenMenuList = selectList("GetOrderScreenMenuList", restAPIVo);
	        		List<HashMap<String, Object>> orderScreenMenuItemList = selectList("GetOrderScreenMenuItemList", restAPIVo);

	        		//최종버전
	    			returnRestAPIVo.setLastVersion(selectOne("GetLastVersion",restAPIVo));

	        		//스크린 메뉴
	    			List<HashMap<String, Object>> orderScreenMenuObj = new ArrayList<HashMap<String, Object>>();
	        		for(int i = 0; i < orderScreenList.size(); i++) {
	        			orderScreenMenuObj = new ArrayList<HashMap<String, Object>>();
	        			for(int j = 0; j < orderScreenMenuList.size(); j++) {
	        				//같은 값일 경우 현재의 행에 메뉴를 추가
	        				if(orderScreenList.get(i).get("ORDER_SCREEN_IDX").equals(orderScreenMenuList.get(j).get("ORDER_SCREEN_IDX"))) {
	        					orderScreenMenuObj.add(orderScreenMenuList.get(j));
	        					List<HashMap<String, Object>> orderScreenMenuItemObj = new ArrayList<HashMap<String, Object>>();
	        					for(int k = 0; k < orderScreenMenuItemList.size(); k++) {
//	        						//같은 값일 경우 현재의 행에 메뉴를 추가
	        						if(orderScreenMenuList.get(j).get("ORDER_SCREEN_IDX").equals(orderScreenMenuItemList.get(k).get("ORDER_SCREEN_IDX"))
	        								&& orderScreenMenuList.get(j).get("ORDER_SCREEN_MENU_IDX").equals(orderScreenMenuItemList.get(k).get("ORDER_SCREEN_MENU_IDX"))
	        						) {
	        							orderScreenMenuItemObj.add(orderScreenMenuItemList.get(k));
	        						}
//
	        					}
	        					orderScreenMenuList.get(j).put("orderScreenMenuItemList",orderScreenMenuItemObj);
	        				}
	        			}
	        			orderScreenList.get(i).put("orderScreenMenuList",orderScreenMenuObj);
	        		}
//	        		resulVo.setOrderScreenList(orderScreenList);

	    			if(orderScreenList.size() == 0) {
	    				errorMessage = "데이터가 한건도 없습니다.";
	    				returnRestAPIVo.setErrorCode("0");
	    			}else {
	    				String jsonList = mapper.writeValueAsString(orderScreenList);
	    				returnRestAPIVo.setRetData(jsonList);
	    				returnRestAPIVo.setRetObjectData(orderScreenList);
	    				returnRestAPIVo.setErrorCode("0");
	    			}

	    		} catch (JsonProcessingException e) {
	    			returnRestAPIVo.setErrorCode("1");
	    			errorMessage = "데이터베이스 오류가 발생하였습니다.";
	    		}

	        }
		} catch (Exception e) {
			returnRestAPIVo.setErrorCode("1");
			errorMessage = "서버오류가 발생하였습니다.";
		}

		returnRestAPIVo.setErrorMessage(errorMessage);

		return returnRestAPIVo;
	}
	
	/**
	 * 상품 조회
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : GetProductList
	 * @date : 2019. 6. 17.
	 * @author : 김한기
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 17.		김한기				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param restAPIVo
	 * @param mapper
	 * @return
	 */
	private RestAPIVo GetProductList(RestAPIVo restAPIVo, ObjectMapper mapper) {
		RestAPIVo returnRestAPIVo = new  RestAPIVo();
//		OrderOptionProdVo resulVo = new OrderOptionProdVo();
		String errorMessage = "";
		//유효성 체크
		try {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	        Validator validator = factory.getValidator();
	        Set<ConstraintViolation<RestAPIVo>> constraintViolations = validator.validate(restAPIVo);
	        if (constraintViolations.size() > 0) {
	            for (ConstraintViolation<RestAPIVo> violation : constraintViolations) {
	                errorMessage += violation.getMessage() ;
	            }
	            restAPIVo.setErrorCode("1");
	        } else {
	        	try {
	        		HashMap<String, Object> idxMap = new HashMap<String, Object>();
//	        		if(restAPIVo.getCaseString().equals("GetProductList")) {
	        			idxMap = selectOne("RestAPI_IdxSearch",restAPIVo.getDeviceCode());
	        			if(idxMap != null) {
	        				restAPIVo.setDomainIdx(idxMap.get("DOMAIN_IDX").toString());
	        				restAPIVo.setBrandIdx(idxMap.get("BRAND_IDX").toString());
	        				restAPIVo.setFrancIdx(idxMap.get("FRANC_IDX").toString());
	        				restAPIVo.setDeviceIdx(BigInteger.valueOf(Long.parseLong(idxMap.get("DEVICE_IDX").toString())));
		        		}
//	        		}
	        		
	        		List<HashMap<String, Object>> productList = selectList("GetProductList", restAPIVo);
	        		List<HashMap<String, Object>> optionProductList = selectList("GetOptionProductList", restAPIVo);
	        		
	        		//최종버전
	    			returnRestAPIVo.setLastVersion(selectOne("GetLastVersion",restAPIVo));

	        		//스크린 메뉴
	        		for(int i = 0; i < productList.size(); i++) {
	        			List<HashMap<String, Object>> optionObj = new ArrayList<HashMap<String, Object>>();
	        			for(int j = 0; j < optionProductList.size(); j++) {
	        				//같은 값일 경우 현재의 행에 옵션을 추가
	        				if(productList.get(i).get("ORDER_PROD_IDX").equals(optionProductList.get(j).get("ORDER_PROD_IDX"))) {
	        					optionObj.add(optionProductList.get(j));
	        					
	        				}
	        			}
	        			productList.get(i).put("optionProductList",optionObj);
	        		}
	        		

	    			if(productList.size() == 0) {
	    				errorMessage = "데이터가 한건도 없습니다.";
	    				returnRestAPIVo.setErrorCode("0");
	    			}else {
	    				String jsonList = mapper.writeValueAsString(productList);
	    				returnRestAPIVo.setRetData(jsonList);
	    				returnRestAPIVo.setRetObjectData(productList);
	    				returnRestAPIVo.setErrorCode("0");
	    			}

	    		} catch (JsonProcessingException e) {
	    			returnRestAPIVo.setErrorCode("1");
	    			errorMessage = "데이터베이스 오류가 발생하였습니다.";
	    		}

	        }
		} catch (Exception e) {
			returnRestAPIVo.setErrorCode("1");
			errorMessage = "서버오류가 발생하였습니다.";
		}

		returnRestAPIVo.setErrorMessage(errorMessage);

		return returnRestAPIVo;
	}
}