package com.wo56.business.ac.impl.builder;

import java.util.Date;

import com.wo56.business.ac.vo.AcAccountDtl;
import com.wo56.business.ac.vo.AcOrderFee;
import com.wo56.common.consts.AccountManageConsts;
import com.wo56.common.utils.OraganizationCacheDataUtil;

/**
 * ��ת -> ��ת��
 * 
 * @author dxb
 */
public class TranferAccountDtlBuilder extends ReversalAcAccountDtl implements ProveAndAccountDtlBuilder {
	private AcOrderFee orderFee;
	private long orgId;
	private long opId;
	private long orderId;
	private long trackingNum;
	private AcAccountDtl accountDtl;
	private int nodeType;
	public TranferAccountDtlBuilder( AcOrderFee orderFee, long orgId, long opId, long orderId, long trackingNum,int nodeType) {
		this.orderFee = orderFee;
		this.orgId = orgId;
		this.opId = opId;
		this.orderId = orderId;
		this.trackingNum = trackingNum;
		this.nodeType = nodeType;
	}

	@Override
	public AcAccountDtl createAcAccountDtl() {
		// �˻�������ϸ����
		if (null == accountDtl){
			accountDtl = new AcAccountDtl();
		}
			
		// �˻�����
		accountDtl.setBusiType(AccountManageConsts.AcAccountDtl.BUSI_TYPE_CASH);
		accountDtl.setAccountId(-1L);//lyh����¼�˻�
		accountDtl.setObjType(AccountManageConsts.Common.OBJ_TYPE_POINT);
		accountDtl.setObjId(orgId);
		accountDtl.setObjName(OraganizationCacheDataUtil.getOrgName(orgId));
		accountDtl.setNodeType(nodeType);

		// ���ò���
		if(orderFee.getPayType() == AccountManageConsts.Common.PAY_TYPE_INCOME){
			accountDtl.setFee(Math.abs(orderFee.getAmount()));
		}else{
			accountDtl.setFee(-Math.abs(orderFee.getAmount()));
		}
		
		accountDtl.setFeeType(orderFee.getFeeType());
		accountDtl.setPaySts(orderFee.getPaySts());
		accountDtl.setPayType(orderFee.getPayType());

		// ���󲿷�
		accountDtl.setFaceObjType(orderFee.getObjType());
		accountDtl.setFaceObjId(orderFee.getObjId());
		accountDtl.setFaceObjName(orderFee.getObjName());
		accountDtl.setFaceAccId(-1L);////lyh����¼�˻�
		
		//�������� dxb ����
		accountDtl.setCheckOrg(orgId);
		accountDtl.setCheckSts(AccountManageConsts.AcCashProve.CHECK_STS_NON);
		accountDtl.setModifyOpId(opId);
		accountDtl.setCheckAmount(0L);
		accountDtl.setWithoutAmount(0L);
		accountDtl.setCheckAmount(0L);
		accountDtl.setCheckRemark("��ת����");
		
		
		// ��������
		Date createDate = new Date();
		accountDtl.setCreateDate(createDate);
		accountDtl.setPayStsModifyDate(createDate);
		accountDtl.setCheckedId(null); //2��ϲ��󲻴��ں���ID
		accountDtl.setTrackingNum(trackingNum);
		accountDtl.setOrderId(orderId);
		accountDtl.setOpId(opId);
		accountDtl.setSysRemark("��ת��¼");
		
		accountDtl.setTenantType(orderFee.getTenantType());
		return accountDtl;
	}

	
}
