package controller;

import java.util.concurrent.Semaphore;

public class ThreadConta extends Thread{
	private int saldo;
	private int codigo;
	private int valor;
	private int cont;
	private Semaphore [] SaqueDeposito;
	private Conta conta[];
	private int pos;
	private static int transacoes;
	
	public ThreadConta(int pos, int codigo, int saldo, int valor, int cont, Semaphore SaqueDeposito[], Conta [] conta) {
		this.codigo = codigo;
		this.saldo = saldo;
		this.valor = valor;
		this.cont = cont;
		this.SaqueDeposito = SaqueDeposito;
		this.conta = conta;
		this.pos = pos;
	}
	
	public void run() {
		try {
			SaqueDeposito[cont].acquire();
			fazTransacao();
			transacoes++;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			mostraResultado();
			SaqueDeposito[cont].release();
			
		}
			
		
	}
	
	public void fazTransacao() {
		conta[pos].setConta(codigo);
		conta[pos].setValor(valor);
		conta[pos].setSaldo(saldo);
		if (conta[pos].getValor() >= 0) {
			conta[pos].setSaldo(conta[pos].getValor()+conta[pos].getSaldo());
		}
		else {
			conta[pos].setSaldo(conta[pos].getSaldo() - conta[pos].getValor());	
		}
		
	}
	
	public void mostraResultado() {
		if (transacoes == 20) {
			for (int i = 0; i<20; i++) {
				if (conta[i].getValor() >= 0) {
				System.out.println("Esta conta fez um depósito.");
				System.out.println("Conta: " + conta[i].getConta() + " | Saldo anterior: R$" + (conta[i].getSaldo() - conta[i].getValor()) + 
						" | Valor depositado:  R$" + conta[i].getValor());
				System.out.println("Saldo atual: R$" + conta[i].getSaldo() + "\n");
				}
				else {
					conta[i].setValor(conta[i].getValor() * -1);
					System.out.println("Esta conta fez um saque.");
					System.out.println("Conta: " + conta[i].getConta() + " | Saldo anterior: R$" + (conta[i].getSaldo() + conta[i].getValor()) + 
						" | Valor sacado:  R$" + conta[i].getValor());
					System.out.println("Saldo atual: R$" + conta[i].getSaldo() +"\n");
				}
			}
			
				
		}
	}

}
