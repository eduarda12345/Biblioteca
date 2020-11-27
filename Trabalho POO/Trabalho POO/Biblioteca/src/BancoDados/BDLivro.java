package BancoDados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Construtoras.Livro;

public class BDLivro {
	
	/* ----CONEX�O COM O BD-> */
    private Connection conexao;
    
    // Estabelece uma conex�o
    public BDLivro() throws SQLException {       
        this.conexao = CriaConexao.getConexao();
    }
    /* <-CONEX�O COM O BD---- */
    
    
    
    
    /* ----LIVRO-> */
    
    // CREATE - Adiciona um registro
    public void adicionaLivro(Livro l) throws SQLException {
        // Prepara conex�o p/ receber o comando SQL
        String sql = "INSERT INTO livro(exemplar, autor, edicao, ano, disponibilidade)"
                + "VALUES(?, ?, ?, ?, ?)";       
        PreparedStatement stmt;
        // stmt recebe o comando SQL
        stmt = this.conexao.prepareStatement(sql);
        
        // Seta os valores p/ o stmt, substituindo os "?"
        stmt.setString(1, l.getExemplar());
        stmt.setString(2, l.getAutor());
        stmt.setString(3, String.valueOf(l.getEdicao()));
        stmt.setString(4, String.valueOf(l.getAno()));
        stmt.setString(5, l.getDisponiblidade());
        
        // O stmt executa o comando SQL no BD, e fecha a conex�o
        stmt.execute();
        stmt.close();
        
    }
    
    // SELECT - Retorna uma lista com o resultado da consulta
    public List<Livro> getLista(String exemplar) throws SQLException{
        // Prepara conex�o p/ receber o comando SQL
        String sql = "SELECT * FROM livro WHERE exemplar like ?";
        PreparedStatement stmt = this.conexao.prepareStatement(sql);
        stmt.setString(1, exemplar);
        
        // Recebe o resultado da consulta SQL
        ResultSet rs = stmt.executeQuery();
        
        List<Livro> lista = new ArrayList<>();
        
        // Enquanto existir registros, pega os valores do ReultSet e vai adicionando na lista
        while(rs.next()) {
            //  A cada loop, � instanciado um novo objeto, p/ servir de ponte no envio de registros p/ a lista
            Livro l = new Livro();
            
            // "c" -> Registro novo - .setNome recebe o campo do banco de String "nome" 
            l.setId(Integer.valueOf(rs.getString("id_livro")));
            l.setExemplar(rs.getString("exemplar"));
            l.setAutor(rs.getString("autor"));
            l.setEdicao(Byte.valueOf(rs.getString("edicao")));
            l.setAno(Short.valueOf(rs.getString("ano")));
            l.setDisponiblidade(rs.getString("disponibilidade"));
            
            // Adiciona o registro na lista
            lista.add(l);            
        }
        
        // Fecha a conex�o com o BD
        rs.close();
        stmt.close();
        
        // Retorna a lista de registros, gerados pela consulta
        return lista;          
    }
    
    // UPDATE - Atualiza registros
    public void altera(Livro l) throws SQLException {
        // Prepara conex�o p/ receber o comando SQL
        String sql = "UPDATE livro set exemplar=?, autor=?, edicao=?, ano=?, disponibilidade=?"
                + "WHERE id_livro=?";
        // stmt recebe o comando SQL
        PreparedStatement stmt = this.conexao.prepareStatement(sql);
        
        // Seta os valores p/ o stmt, substituindo os "?"        
        stmt.setString(1, l.getExemplar());
        stmt.setString(2, l.getAutor());
        stmt.setString(3, String.valueOf(l.getEdicao()));
        stmt.setString(4, String.valueOf(l.getAno()));
        stmt.setString(5, l.getDisponiblidade());
        // Usa o ID como par�metro de compara��o no SQL
        stmt.setInt(6, l.getId());
        
        // O stmt executa o comando SQL no BD, e fecha a conex�o
        stmt.execute();
        stmt.close();
    }
    
    // UPDATE - Altera a disponibilidade do livro
    public void alteraDisponibilidadeLivro(Livro l) throws SQLException {
        // Prepara conex�o p/ receber o comando SQL
        String sql = "UPDATE livro set disponibilidade=?"
                + "WHERE id_livro=?";
        // stmt recebe o comando SQL
        PreparedStatement stmt = this.conexao.prepareStatement(sql);
        
        // Seta os valores p/ o stmt, substituindo os "?"  
        stmt.setString(1, l.getDisponiblidade());
        // Usa o ID como par�metro de compara��o no SQL
        stmt.setInt(2, l.getId());
        
        // O stmt executa o comando SQL no BD, e fecha a conex�o
        stmt.execute();
        stmt.close();
    }
    
    // DELETE - Apaga registros
    public void remove(int id) throws SQLException {       
        // Prepara conex�o p/ receber o comando SQL
        String sql = "DELETE FROM livro WHERE id_livro=?";
        // stmt recebe o comando SQL
        PreparedStatement stmt = this.conexao.prepareStatement(sql);
        
        // Seta o valor do ID p/ a condi��o de verifica��o SQL, dentro do stmt
        stmt.setInt(1, id);
        
        // Executa o codigo SQL, e fecha
        stmt.execute();
        stmt.close();
        
    }
    /* <-LIVRO---- */
}

