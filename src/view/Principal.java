package view;

import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Principal {
	//vetor com a posição final dos participantes
	private static int[] vencedores;
	private static int[] premios;
	
	/*
	método que é puxado no final de cada thread com intuito de jogar o participante
	vetor de vencedores
	*/
	public static void finalizar(int participante) {
		int c = 0;
		while(c < 10) {
			if(vencedores[c] == 0) {
				vencedores[c] = participante;
				break;
			}
			c++;
		}
	}
	
	//execução do programa
	public static void execucao() {
		// declaração de vetores
				ThreadCassino[] jogadores = new ThreadCassino[10];
				vencedores = new int[10];
				for(int c = 0; c < 10; c++) {
					jogadores[c] = new ThreadCassino(c+1, 5);
				}
				
				/*
				cyclicbarrier serve para fazer todas as threads iniciarem juntas 
				(11 = 11 gate.await() para iniciar de verdade)
				isso gera +10 threads (***ver depois se existe um jeito melhor)
				*/
				final CyclicBarrier gate = new CyclicBarrier(11);
				Thread[] inicializador = new Thread[10];
				
				
				for(int c = 0; c < 10; c++) {
					//final d --> jogadores[d].start() estava pedindo um final
					final int d = c;
					inicializador[c]= new Thread(){
					    public void run(){
					    	try {
								gate.await();
								jogadores[d].start();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (BrokenBarrierException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					    }};
					inicializador[c].start();
				}
				
				try {
					gate.await();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BrokenBarrierException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				/*
				 * sleep para dar tempo de todas as threads terminarem 
				 * (1000 pode não dar tempo caso a thread tenha azar)
				 */
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//sysout com a posição final
				for(int c = 0; c < 10; c++) {
					int premio = 5000 - (c * 1000);
					if(premio < 3000) {
						premio = 0;
					}
					premios[vencedores[c] - 1] += premio;
					System.out.println((c+1) + "° lugar: " + vencedores[c] + ", Prêmio: " + premio + ", Total: " + premios[vencedores[c] - 1]);
					
				}
	}
	
	//limpa o vetor de vencedores
	private static void limpar() {
		for(int c = 0; c < 10; c++) {
			vencedores[c] = 0;
		}
	}
	
	//main contendo metodo de execucao e loop
	public static void main(String[] args) {
		premios = new int[10];
		Scanner in = new Scanner(System.in);
		String s;
		do {
			System.out.println("--------------------------");
			execucao();
			limpar();
			System.out.println("--------------------------");
			System.out.println("Deseja jogar novamente? (S/N)");
			s = in.nextLine();
		}while(s.equals("S") || s.equals("s"));
	}

}
