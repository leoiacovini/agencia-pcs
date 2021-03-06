package pcs.labsoft.agencia.models;

public class Hotel implements Comparable<Hotel>{

	private final int id;
	private final String nome;
	private final double preco;
	private final Cidade cidade;

	public Hotel(String nome, double preco, Cidade cidade, int id) {
		this.nome = nome;
		this.preco = preco;
		this.cidade = cidade;
		this.id = id;
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public double getPreco()
	{
		return preco;
	}
	
	public int getID()
	{
		return id;
	}

	public Cidade getCidade() {
		return cidade;
	}

	@Override
	public int compareTo(Hotel h) {
		return this.preco > h.preco ? 1 : (this.preco < h.preco ? -1 : 0);
	}
}
