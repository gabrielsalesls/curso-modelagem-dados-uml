package io.github.gabrielsalesls.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import io.github.gabrielsalesls.cursomc.domain.Categoria;
import io.github.gabrielsalesls.cursomc.domain.Cidade;
import io.github.gabrielsalesls.cursomc.domain.Cliente;
import io.github.gabrielsalesls.cursomc.domain.Endereco;
import io.github.gabrielsalesls.cursomc.domain.Estado;
import io.github.gabrielsalesls.cursomc.domain.ItemPedido;
import io.github.gabrielsalesls.cursomc.domain.Pagamento;
import io.github.gabrielsalesls.cursomc.domain.PagamentoComBoleto;
import io.github.gabrielsalesls.cursomc.domain.PagamentoComCartao;
import io.github.gabrielsalesls.cursomc.domain.Pedido;
import io.github.gabrielsalesls.cursomc.domain.Produto;
import io.github.gabrielsalesls.cursomc.domain.enums.EstadoPagamento;
import io.github.gabrielsalesls.cursomc.domain.enums.TipoCliente;
import io.github.gabrielsalesls.cursomc.repositories.CategoriaRepository;
import io.github.gabrielsalesls.cursomc.repositories.CidadeRepository;
import io.github.gabrielsalesls.cursomc.repositories.ClienteRepository;
import io.github.gabrielsalesls.cursomc.repositories.EnderecoRepository;
import io.github.gabrielsalesls.cursomc.repositories.EstadoRepository;
import io.github.gabrielsalesls.cursomc.repositories.ItemPedidoRepository;
import io.github.gabrielsalesls.cursomc.repositories.PagamentoRepository;
import io.github.gabrielsalesls.cursomc.repositories.PedidoRepository;
import io.github.gabrielsalesls.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursoModelagemDadosApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursoModelagemDadosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
			
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		
		Estado estado1 = new Estado(null, "Minas Gerais");
		Estado estado2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", estado1);
		Cidade c2 = new Cidade(null, "São Paulo", estado2);
		Cidade c3 = new Cidade(null, "Campinas", estado2);
		
		estado1.getCidades().addAll(Arrays.asList(c1));
		estado2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(estado1, estado2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cliente1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "35456123156", TipoCliente.PESSOAFISICA);
		cliente1.getTelefones().addAll(Arrays.asList("27363323", "92838292"));
		
		Endereco endereco1 = new Endereco(null, "Rua flores", "300", "Apto 303", "Jardim", "46512378987", cliente1, c1);
		Endereco endereco2 = new Endereco(null, "Avenida Matos", "105", "Sala 300", "Centro",  "38777012", cliente1, c2);
		
		cliente1.getEnderecoes().addAll(Arrays.asList(endereco1, endereco2));
		
		clienteRepository.saveAll(Arrays.asList(cliente1));
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido pedido1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cliente1, endereco1);
		Pedido pedido2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cliente1, endereco2);
		
		Pagamento pagamento1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
		pedido1.setPagamento(pagamento1);
		
		Pagamento pagamento2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2, sdf.parse("20/10/2017 00:00"), null);
		pedido2.setPagamento(pagamento2);
		
		cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
		
		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));
		
		ItemPedido ip1 = new ItemPedido(pedido1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(pedido1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(pedido2, p2, 100.00, 1, 800.00);
		
		pedido1.getItens().addAll(Arrays.asList(ip1, ip2));
		pedido2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
		
	}

}
