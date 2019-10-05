package model.service;

import java.util.Calendar;
import java.util.Date;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {
	
	private OnlinePaymentService onpay;

	public ContractService(OnlinePaymentService onpay) {
		this.onpay = onpay;
	}
	
	public void processContract(Contract contract, int months) {
		double basicQuota = contract.getTotalValue() / months;
		
		for (int i = 0; i <= months; i++) {
			Date date = addMonth(contract.getDate(), i);
			double updateQuota = basicQuota + onpay.interest(basicQuota, months);
			double fullQuota = updateQuota + onpay.paymentFee(updateQuota);
			contract.addInstallment(new Installment(date, fullQuota));
		}
	}
	
	public Date addMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}
}
