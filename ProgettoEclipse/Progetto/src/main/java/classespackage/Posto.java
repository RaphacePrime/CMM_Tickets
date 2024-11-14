package classespackage;

public class Posto {
	
	private int idPosto;
	private int num;
	public Settore settore;
	
	public Posto(int idPosto, int num, Settore settore)
	{
		this.setIdPosto(idPosto);
		this.setNum(num);
		this.settore=settore;
		
	}
	
	public Posto(int num, Settore settore)
	{
		this.setNum(num);
		this.settore=settore;
		
	}

	public int getIdPosto() {
		return idPosto;
	}

	public void setIdPosto(int idPosto) {
		this.idPosto = idPosto;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	
}
