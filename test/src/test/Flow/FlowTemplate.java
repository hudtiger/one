package test.Flow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class FlowTemplate {

	public List<Step> getSteps(Step step){
		List<Step> list = new ArrayList();
		list.add(step);
		getSteps(step,list);
		return list;
	}
	
	private void getSteps(Step step,List<Step> list) {
		if(step.subStep!=null) {
			list.add(step.subStep);
			getSteps(step.subStep,list);
		}
		if(step.nextStep!=null) {
			list.add(step.nextStep);
			getSteps(step.nextStep,list);
		}
	}
	
	public static void main(String[] args) {
		Step step = new Step("Step1",StepState.START)
				.setNextStep(new Step("Step2",StepState.START)
						.setSubStep(new Step("Step2-SubStart",StepState.START,"Zhou","Jhon","Steven")
								.setNextStep(new Step("Step2-SubNext1",StepState.START,"Jerry")
										.setNextStep(new Step("Step2-SubNext2",StepState.START,"Tomy"))))
						.setNextStep(new Step("Step3",StepState.START)))
				.setSubStep(new Step("Step1-SubStart1",StepState.START,"Micheal","Chen")
						.setNextStep(new Step("Step1-subNext2",StepState.START,"Zhou")));
		print(step);
		step.start(null);
		
		step.upState("Step1-SubStart1", StepState.PASS);
		step.upState("Step1-subNext2", StepState.PASS);
//		step.upState("Step2-SubStart", StepState.PASS);
//		step.upState("Step2-SubNext1", StepState.PASS);
//		step.upState("Step2-SubNext2", StepState.PASS);
//		step.upState("Step3", StepState.PASS);
		step.upState("Step2-SubStart", StepState.STOP);
		
		
		FlowTemplate tmp = new FlowTemplate();
	    tmp.getSteps(step).forEach(item->System.out.println(item.caption.concat(":").concat(item.state.toString())));
	    
	    
	    
//	    print(step.getStep("Step2"));
    
	}
	
	public static void print(Step step) {
		System.out.println(JSON.toJSONString(step,SerializerFeature.PrettyFormat));
	}

}

enum StepState{
	START(0),PROCESS(1),PASS(2),STOP(3),COMPLETE(4);
	
	Integer code;
	StepState(Integer code) {
        this.code = code;
    }

    public Integer code() {
        return this.code;
    }
}

class Step{
	String caption;
	String[] Operator;
	StepState state;
	Step nextStep=null;
	Step subStep = null;

	public Step() {}
	
	public Step(String caption,StepState state,String ... operator) {
		this.caption = caption;
		this.Operator = operator;
		this.state = state;
	}
	
	//查找结点
	public Step getStep(String caption) {
		for(Step step : getSteps(this)) {
			if(caption.equals(step.caption))
				return step;
		}
		return null;
	}
	
	private List<Step> getSteps(Step step){
		List<Step> list = new ArrayList();
		list.add(step);
		getSteps(step,list);
		return list;
	}
	
	private void getSteps(Step step,List<Step> list) {
		if(step.subStep!=null) {
			list.add(step.subStep);
			getSteps(step.subStep,list);
		}
		if(step.nextStep!=null) {
			list.add(step.nextStep);
			getSteps(step.nextStep,list);
		}
	}
	//查找主流程节点
	private List<Step> getMainStep() {
		return getSteps(this).stream().filter(step->step.state==StepState.PROCESS).collect(Collectors.toList());
	}
	
	//节点启动
	public void start(Step step) {
		if(step == null)
			step = this;
		step.state = StepState.PROCESS;
		if(step.subStep!=null) {
			start(step.subStep);
		}
	}
	
	//修改节点状态
	public void upState(String caption,StepState state) {
		List<Step> list = this.getSteps(this);
		List<Step> activeSteps = getMainStep();
		int idx = list.indexOf(activeSteps.get(activeSteps.size()-1));
		Step step = this.getStep(caption);
		if(state == StepState.STOP) {
			activeSteps.forEach(activeStep->activeStep.state=state);
		}else if(state==StepState.PASS) {
			step.state = state;
			if(step.nextStep!=null) { 
				step.start(step.nextStep);
			}else {	
				activeSteps.forEach(activeStep->activeStep.state=state);
				System.out.println(idx);
				if(list.size()>idx+1)
					this.start(list.get(idx+1));
				else
					step.state=StepState.COMPLETE;
			}
		}
	}
	
	public Step getSubStep() {
		return subStep;
	}

	public Step setSubStep(Step subStep) {
		this.subStep = subStep;
		return this;
	}

	public String getCaption() {
		return caption;
	}

	public Step setCaption(String caption) {
		this.caption = caption;
		return this;
	}
	public String[] getOperator() {
		return Operator;
	}
	public Step setOperator(String[] operator) {
		Operator = operator;
		return this;
	}
	public StepState getState() {
		return state;
	}
	public Step setState(StepState state) {
		this.state = state;
		return this;
	}
	public Step getNextStep() {
		return nextStep;
	}
	public Step setNextStep(Step nextStep) {
		this.nextStep = nextStep;
		return this;
	}
	
}
