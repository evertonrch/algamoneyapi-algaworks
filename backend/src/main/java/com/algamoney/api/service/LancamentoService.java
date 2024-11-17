package com.algamoney.api.service;

import com.algamoney.api.dto.LancamentoEstatisticaPessoa;
import com.algamoney.api.model.Lancamento;
import com.algamoney.api.model.Pessoa;
import com.algamoney.api.repository.LancamentoRepository;
import com.algamoney.api.repository.PessoaRepository;
import com.algamoney.api.repository.filter.LancamentoFilter;
import com.algamoney.api.repository.projection.ResumoLancamento;
import com.algamoney.api.service.exception.PessoaInativaException;
import com.algamoney.api.service.exception.PessoaInexistenteException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;


    public Page<Lancamento> getLancamentos(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return lancamentoRepository.filtrar(lancamentoFilter, pageable);
    }

    public Page<ResumoLancamento> getResumo(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return lancamentoRepository.resumir(lancamentoFilter, pageable);
    }

    public Lancamento getLancamentoById(Long id) {
        Optional<Lancamento> optLancamento = lancamentoRepository.findById(id);
        return optLancamento.orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    public Lancamento createLancamento(Lancamento lancamentoRequest) {
        Optional<Pessoa> optPessoa = pessoaRepository.findById(lancamentoRequest.getPessoa().getId());
        if (optPessoa.isEmpty())
            throw new PessoaInexistenteException();

        Pessoa pessoa = optPessoa.get();
        if (pessoa.isInativo())
            throw new PessoaInativaException();

        return lancamentoRepository.save(lancamentoRequest);
    }

    public void deleteLancamento(Long id) {
        lancamentoRepository.deleteById(id);
    }

    public byte[] relatorioPorPessoa(LocalDate dtInicio, LocalDate dtFim) throws IOException, JRException {
        List<LancamentoEstatisticaPessoa> dados = lancamentoRepository.porPessoa(dtInicio, dtFim);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("DT_INICIO", Date.valueOf(dtInicio)); // sql.Date método que faz parsing de LocalDate p/ Date
        parametros.put("DT_FIM", Date.valueOf(dtFim)); // sql.Date método que faz parsing de LocalDate p/ Date
        parametros.put("REPORT_LOCALET", new Locale("pt", "BR"));

        try(InputStream fluxo = this.getClass().getResourceAsStream("/relatorios/lancamentos-por-pessoa.jasper")){
            JasperPrint jasperPrint = JasperFillManager.fillReport(fluxo, parametros,
                    new JRBeanCollectionDataSource(dados));

            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }
}
