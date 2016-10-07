package pcs.labsoft.agencia.models;

public class Hotel {
	private static int lastID = 1;
	private int ID;
	private String nome;
	private double preco;
	
	public Hotel(String n, double p)
	{
		nome = n;
		preco = p;
		ID = lastID;
		lastID++;
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
		return ID;
	}
}
