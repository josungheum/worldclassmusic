package qss.vo;

import java.util.List;
import java.util.Map;

public class RestAPIRequestVo extends CommonVo {
	private static final long serialVersionUID = 1L;

	private String KIOSK_KEY;
	private String KIOSK_ID;
	private String KIOSK_TYPE;
	private int VERSION;
	private String downloadPath;
	private String KIOSK_CLIENT_VERSION;

	// version 조회
	private ScheduleVersionVo scheduleVersionVo;

	// schedule 조회
	private List<ContentResultVo> contentResultVo;
	private List<LayerResultVo> layerResultVo;
	private List<ScreenResultVo> screenResultVo;
	private List<ScheduleResultVo> scheduleResultVo;
	
	// menu 조회
	private List<MenuResultVo> menuResultVo;
	private List<OrderProdResultVo> orderProdResultVo;
	private List<ProdSetResultVo> prodSetResultVo;
	private List<ProdSetItemResultVo> prodSetItemResultVo;
	
	// domainSchedule 조회
	private DomainScheduleVo domainScheduleVo;
	
	// brandInfo 조회
	private BrandResultVo brandResultVo;
	private List<DiscountResultVo> discountResultVo;
	
	// Franchisee 조회
	private FranchiseeResultVo franchiseeResultVo;
	private List<KioskWaitingResultVo> KioskWaitingResultVo;
	private List<KioskVo> KioskVo;
	
	// Insert Map
	private Map<String, Object> requestParam;
	
	// 결제 등록 후 정보 결과
	private OrderMasterVo orderMaster;
	private OrderDocResultVo orderDocResultVo;
	
	// 영수증 정보
	private FrancReceiptResultVo francReceiptResultVo;
	
	private List<KioskClientVo> kioskClientVo;

	public String getKIOSK_KEY() {
		return KIOSK_KEY;
	}

	public void setKIOSK_KEY(String kIOSK_KEY) {
		KIOSK_KEY = kIOSK_KEY;
	}

	public String getKIOSK_ID() {
		return KIOSK_ID;
	}

	public void setKIOSK_ID(String kIOSK_ID) {
		KIOSK_ID = kIOSK_ID;
	}

	public String getKIOSK_TYPE() {
		return KIOSK_TYPE;
	}

	public void setKIOSK_TYPE(String kIOSK_TYPE) {
		KIOSK_TYPE = kIOSK_TYPE;
	}

	public int getVERSION() {
		return VERSION;
	}

	public void setVERSION(int vERSION) {
		VERSION = vERSION;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}


	public ScheduleVersionVo getScheduleVersionVo() {
		return scheduleVersionVo;
	}

	public void setScheduleVersionVo(ScheduleVersionVo scheduleVersionVo) {
		this.scheduleVersionVo = scheduleVersionVo;
	}

	public List<ContentResultVo> getContentResultVo() {
		return contentResultVo;
	}

	public void setContentResultVo(List<ContentResultVo> contentResultVo) {
		this.contentResultVo = contentResultVo;
	}

	public List<LayerResultVo> getLayerResultVo() {
		return layerResultVo;
	}

	public void setLayerResultVo(List<LayerResultVo> layerResultVo) {
		this.layerResultVo = layerResultVo;
	}

	public List<ScreenResultVo> getScreenResultVo() {
		return screenResultVo;
	}

	public void setScreenResultVo(List<ScreenResultVo> screenResultVo) {
		this.screenResultVo = screenResultVo;
	}

	public List<ScheduleResultVo> getScheduleResultVo() {
		return scheduleResultVo;
	}

	public void setScheduleResultVo(List<ScheduleResultVo> scheduleResultVo) {
		this.scheduleResultVo = scheduleResultVo;
	}

	public List<MenuResultVo> getMenuResultVo() {
		return menuResultVo;
	}

	public void setMenuResultVo(List<MenuResultVo> menuResultVo) {
		this.menuResultVo = menuResultVo;
	}

	public List<OrderProdResultVo> getOrderProdResultVo() {
		return orderProdResultVo;
	}

	public void setOrderProdResultVo(List<OrderProdResultVo> orderProdResultVo) {
		this.orderProdResultVo = orderProdResultVo;
	}

	

	public List<ProdSetResultVo> getProdSetResultVo() {
		return prodSetResultVo;
	}

	public void setProdSetResultVo(List<ProdSetResultVo> prodSetResultVo) {
		this.prodSetResultVo = prodSetResultVo;
	}

	public List<ProdSetItemResultVo> getProdSetItemResultVo() {
		return prodSetItemResultVo;
	}

	public void setProdSetItemResultVo(List<ProdSetItemResultVo> prodSetItemResultVo) {
		this.prodSetItemResultVo = prodSetItemResultVo;
	}

	public DomainScheduleVo getDomainScheduleVo() {
		return domainScheduleVo;
	}

	public void setDomainScheduleVo(DomainScheduleVo domainScheduleVo) {
		this.domainScheduleVo = domainScheduleVo;
	}

	public BrandResultVo getBrandResultVo() {
		return brandResultVo;
	}

	public void setBrandResultVo(BrandResultVo brandResultVo) {
		this.brandResultVo = brandResultVo;
	}

	public List<DiscountResultVo> getDiscountResultVo() {
		return discountResultVo;
	}

	public void setDiscountResultVo(List<DiscountResultVo> discountResultVo) {
		this.discountResultVo = discountResultVo;
	}

	public Map<String, Object> getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(Map<String, Object> requestParam) {
		this.requestParam = requestParam;
	}

	public FranchiseeResultVo getFranchiseeResultVo() {
		return franchiseeResultVo;
	}

	public void setFranchiseeResultVo(FranchiseeResultVo franchiseeResultVo) {
		this.franchiseeResultVo = franchiseeResultVo;
	}

	

	public List<KioskWaitingResultVo> getKioskWaitingResultVo() {
		return KioskWaitingResultVo;
	}

	public void setKioskWaitingResultVo(List<KioskWaitingResultVo> list) {
		KioskWaitingResultVo = list;
	}

	public List<KioskVo> getKioskVo() {
		return KioskVo;
	}

	public void setKioskVo(List<KioskVo> kioskVo) {
		KioskVo = kioskVo;
	}

	public OrderMasterVo getOrderMaster() {
		return orderMaster;
	}

	public void setOrderMaster(OrderMasterVo orderMaster) {
		this.orderMaster = orderMaster;
	}

	public FrancReceiptResultVo getFrancReceiptResultVo() {
		return francReceiptResultVo;
	}

	public void setFrancReceiptResultVo(FrancReceiptResultVo francReceiptResultVo) {
		this.francReceiptResultVo = francReceiptResultVo;
	}

	public OrderDocResultVo getOrderDocResultVo() {
		return orderDocResultVo;
	}

	public void setOrderDocResultVo(OrderDocResultVo orderDocResultVo) {
		this.orderDocResultVo = orderDocResultVo;
	}

	public String getKIOSK_CLIENT_VERSION() {
		return KIOSK_CLIENT_VERSION;
	}

	public void setKIOSK_CLIENT_VERSION(String kIOSK_CLIENT_VERSION) {
		KIOSK_CLIENT_VERSION = kIOSK_CLIENT_VERSION;
	}

	public List<KioskClientVo> getKioskClientVo() {
		return kioskClientVo;
	}

	public void setKioskClientVo(List<KioskClientVo> kioskClientVo) {
		this.kioskClientVo = kioskClientVo;
	}
	
}
