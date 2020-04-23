package view;

//Thread responsável pela execução dos 10 participantes do cassino

public class ThreadCassino extends Thread{
	
	//Atributo pontos controla a quantidade de pontos necessários para vencer (é recebido via construtor)
	private int pontos;
	private int numeroParticipante;

	public ThreadCassino(int numeroParticipante, int pontos) {
		this.numeroParticipante = numeroParticipante;
		this.pontos = pontos;
	}
	
	//Método "joga" o jogo, pontuando toda vez que a soma dos dados for igual a 7 ou 11,
	//acabando quando a quantidade de pontos for igual ao estipulado
	private void jogar() {
		int pontosJogador = 0;
		while(pontosJogador < pontos) {
			int dado1 = (int) (1 + (Math.random() * 6));
			int dado2 = (int) (1 + (Math.random() * 6));
			if(dado1 + dado2 == 7 || dado1 + dado2 == 11) {
				pontosJogador++;
			}
		}
	}

	public void run() {
		jogar();
		Principal.finalizar(numeroParticipante);
	}

}
