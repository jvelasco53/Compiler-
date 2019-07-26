package other;

import java.util.List;

public class CallExpressions extends Expressions{
	private String id;
	private List<Expressions> exrp;
	public CallExpressions(String id, List<Expressions> exrp) {
		this.id = id;
		this.exrp = exrp;
	}
	@Override
	public void display(String indent) {
		// TODO Auto-generated method stub
		System.out.println(indent+"call " + id);
		for (Expressions s: exrp) {
			s.display(indent+" ");
		}
	}
	

}
