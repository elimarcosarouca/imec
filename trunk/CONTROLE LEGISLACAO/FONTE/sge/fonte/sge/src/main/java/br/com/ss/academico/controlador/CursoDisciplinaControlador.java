package br.com.ss.academico.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import br.com.ss.academico.dominio.Curso;
import br.com.ss.academico.dominio.CursoDisciplina;
import br.com.ss.academico.dominio.CursoDisciplinaId;
import br.com.ss.academico.dominio.Disciplina;
import br.com.ss.academico.servico.CursoDisciplinaServico;
import br.com.ss.core.web.utils.FacesUtils;

@ManagedBean
@SessionScoped
public class CursoDisciplinaControlador {

	private static final String MSG_ADICIONAR = "Disciplina adicionado com sucesso.";
	private static final String MSG_REMOVER = "Disciplina removido com sucesso.";

	private static final String MSG_ERRO = "Ocorreu um erro ao executar a operação!";

	@ManagedProperty(value = "#{cursoDisciplinaServicoImpl}")
	private CursoDisciplinaServico cursoDisciplinaServico;

	private DualListModel<CursoDisciplina> dualListDisciplina;

	private List<CursoDisciplina> listaCursoDisciplinaNotInCurso;
	private Curso cursoModalDisciplina;

	@PostConstruct
	public void init() {
		dualListDisciplina = new DualListModel<CursoDisciplina>();
	}

	public void showModalDisciplina(Curso curso) {
		this.cursoModalDisciplina = curso;
		listaCursoDisciplinaNotInCurso = new ArrayList<CursoDisciplina>();
		List<Disciplina> listaDisciplinaNotInCurso = cursoDisciplinaServico
				.listaDisciplinaNotInCurso(curso.getId());

		for (Disciplina disciplina : listaDisciplinaNotInCurso) {
			CursoDisciplina cursoDisciplina = createCursoDisciplina(disciplina, curso);
			listaCursoDisciplinaNotInCurso.add(cursoDisciplina);
		}
		// faz o fetch de CursoDisciplina
		List<CursoDisciplina> cursoDisciplinas = cursoDisciplinaServico.findByCurso(curso);
		curso.setCursoDisciplina(cursoDisciplinas);
		dualListDisciplina = new DualListModel<CursoDisciplina>(
									listaCursoDisciplinaNotInCurso,
									curso.getCursoDisciplina());
	}

	private CursoDisciplina createCursoDisciplina(Disciplina disciplina, Curso curso) {
		CursoDisciplina cursoDisciplina = new CursoDisciplina();
		cursoDisciplina.setData(new Date());
		cursoDisciplina.setDisciplina(disciplina);
		cursoDisciplina.setCurso(curso);
		return cursoDisciplina;
	}

	@SuppressWarnings("unchecked")
	public void onTransfer(TransferEvent event) {

		List<CursoDisciplina> cursoDisciplinas = (List<CursoDisciplina>) event.getItems();
		
		boolean add = event.isAdd();
		
		for (CursoDisciplina cursoDisciplina : cursoDisciplinas) {
			salvarCurso(cursoDisciplina, add);
		}
		
		String msg = MSG_ADICIONAR;
		if (!add) {
			msg = MSG_REMOVER;
		}

		FacesUtils.addMessage(msg, null, FacesMessage.SEVERITY_INFO);
		
	}

	private void salvarCurso(CursoDisciplina cursoDisciplina, boolean add) {

		try {
			
			if (add) {
				
				CursoDisciplinaId id = new CursoDisciplinaId();
				id.setCurso(cursoDisciplina.getCurso());
				id.setDisciplina(cursoDisciplina.getDisciplina());
				cursoDisciplina.setId(id);
				
				cursoDisciplinaServico.salvar(cursoDisciplina);
				
			} else {
				
				// FIXME #Peninha: validar se a a relacao (CursoDisciplina) está em uso (boletim) antes de excluir
				// exibir alerta exibindo msg q nao pode excluir pois o CursoDisciplina está em uso..
				
				cursoDisciplinaServico.remover(cursoDisciplina);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			FacesUtils.addMessage(MSG_ERRO, null, FacesMessage.SEVERITY_ERROR);
		}

	}


	/* ---------- Gets/Sets --------------- */

	public CursoDisciplinaServico getCursoDisciplinaServico() {
		return cursoDisciplinaServico;
	}

	public void setCursoDisciplinaServico(
			CursoDisciplinaServico cursoDisciplinaServico) {
		this.cursoDisciplinaServico = cursoDisciplinaServico;
	}

	public DualListModel<CursoDisciplina> getDualListDisciplina() {
		return dualListDisciplina;
	}

	public void setDualListDisciplina(
			DualListModel<CursoDisciplina> dualListDisciplina) {
		this.dualListDisciplina = dualListDisciplina;
	}

	public Curso getCursoModalDisciplina() {
		return cursoModalDisciplina;
	}

	public void setCursoModalDisciplina(Curso cursoModalDisciplina) {
		this.cursoModalDisciplina = cursoModalDisciplina;
	}
}
