import { Component, Input, OnInit } from '@angular/core';
import { Pessoa } from '../pessoas-pesquisa/pessoas-pesquisa.component';

@Component({
  selector: 'app-pessoas-grid',
  templateUrl: './pessoas-grid.component.html',
  styleUrls: ['./pessoas-grid.component.css']
})
export class PessoasGridComponent {

  @Input() pessoas: Pessoa[] = []

  public isAtivo(ativo: boolean, inverte: boolean = false): string {
    if(inverte) {
      return ativo ? "Desativar" : "Ativar"
    }
    return ativo ? "Ativo" : "Inativo"
  }
}
