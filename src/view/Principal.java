package view;

import java.util.concurrent.Semaphore;

import controller.Conta;
import controller.ThreadConta;

public class Principal {
	public static void main(String[] args) {
		
		int codigo = 0;
		int saldo = 0;
		int valor = 0;
		int cont = -1;
		Semaphore [] SaqueDeposito = new Semaphore[2];
		Conta [] conta = new Conta[20];
		
		for (int i =0; i<2; i++) {
			SaqueDeposito[i] = new Semaphore(1);
		}
		
		
		for (int i = 0; i<20; i++) {
			conta[i] = new Conta();
			
			codigo = (int)(Math.random() * 100)+100;
			saldo = (int)(Math.random() * 2000)+1000;
			valor = (int)(Math.random()* 400)-200;
			if (valor >= 0 ) {
				cont = 1;
			}
			else {
				cont = 0;
			}
			Thread transacao = new ThreadConta (i, codigo, saldo, valor, cont, SaqueDeposito, conta);
			transacao.start();
		}
	}

}
