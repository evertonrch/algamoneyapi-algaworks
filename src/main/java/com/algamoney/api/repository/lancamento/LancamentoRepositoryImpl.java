package com.algamoney.api.repository.lancamento;

import com.algamoney.api.model.Categoria_;
import com.algamoney.api.model.Lancamento;
import com.algamoney.api.model.Lancamento_;
import com.algamoney.api.model.Pessoa_;
import com.algamoney.api.repository.filter.LancamentoFilter;
import com.algamoney.api.repository.projection.ResumoLancamento;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);


        TypedQuery<Lancamento> query = entityManager.createQuery(criteria);
        restricoesPaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
    }

    private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder, Root<Lancamento> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
            Predicate likeDescricao = builder.like(builder.lower(root.get(Lancamento_.descricao)), "%" + lancamentoFilter.getDescricao() + "%");
            predicates.add(likeDescricao);
        }
        if (lancamentoFilter.getDataVencimentoFrom() != null) {
            Predicate gteDataVencimento = builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoFrom());
            predicates.add(gteDataVencimento);
        }
        if (lancamentoFilter.getDataVencimentoTo() != null) {
            Predicate lteDataVencimento = builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoTo());
            predicates.add(lteDataVencimento);
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }


    private void restricoesPaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int registrosPorPagina = pageable.getPageSize();
        int primeiroRegistroPagina = paginaAtual * registrosPorPagina;

        query.setFirstResult(primeiroRegistroPagina);
        query.setMaxResults(registrosPorPagina);
    }

    private Long total(LancamentoFilter lancamentoFilter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return entityManager.createQuery(criteria).getSingleResult();
    }

    @Override
    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        criteria.select(builder.construct(ResumoLancamento.class,
                root.get(Lancamento_.id), root.get(Lancamento_.descricao),
                root.get(Lancamento_.dataPagamento), root.get(Lancamento_.dataVencimento),
                root.get(Lancamento_.valor), root.get(Lancamento_.tipoLancamento),
                root.get(Lancamento_.categoria).get(Categoria_.nome),
                root.get(Lancamento_.pessoa).get(Pessoa_.nome)));

        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<ResumoLancamento> query = entityManager.createQuery(criteria);
        restricoesPaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
    }
}
