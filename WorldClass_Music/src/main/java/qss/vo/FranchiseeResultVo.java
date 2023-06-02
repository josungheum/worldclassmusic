package qss.vo;

import java.util.List;

public class FranchiseeResultVo {
	private String Name;
	private String PrintYN;
	private String UseEnglishYN;
	private String UseTakeOutYN;
	private String TakeOutText;
	private String EnTakeOutText;
	private String UseDiscountYN;
	private List<KioskWaitingResultVo> KioskWaitingList;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	

	public String getPrintYN() {
		return PrintYN;
	}

	public void setPrintYN(String printYN) {
		PrintYN = printYN;
	}

	public String getUseEnglishYN() {
		return UseEnglishYN;
	}

	public void setUseEnglishYN(String useEnglishYN) {
		UseEnglishYN = useEnglishYN;
	}

	public String getUseTakeOutYN() {
		return UseTakeOutYN;
	}

	public void setUseTakeOutYN(String useTakeOutYN) {
		UseTakeOutYN = useTakeOutYN;
	}

	public String getTakeOutText() {
		return TakeOutText;
	}

	public void setTakeOutText(String takeOutText) {
		TakeOutText = takeOutText;
	}

	public String getEnTakeOutText() {
		return EnTakeOutText;
	}

	public void setEnTakeOutText(String enTakeOutText) {
		EnTakeOutText = enTakeOutText;
	}
	
	public String getUseDiscountYN() {
		return UseDiscountYN;
	}

	public void setUseDiscountYN(String useDiscountYN) {
		UseDiscountYN = useDiscountYN;
	}

	public List<KioskWaitingResultVo> getKioskWaitingList() {
		return KioskWaitingList;
	}

	public void setKioskWaitingList(List<KioskWaitingResultVo> kioskWaitingList) {
		KioskWaitingList = kioskWaitingList;
	}
}
