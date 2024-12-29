import { Component } from '@angular/core';

export interface Pessoa {
  nome: string,
  cidade: string,
  estado: string,
  ativo: boolean
}

@Component({
  selector: 'app-pessoas-pesquisa',
  templateUrl: './pessoas-pesquisa.component.html',
  styleUrls: ['./pessoas-pesquisa.component.css']
})
export class PessoasPesquisaComponent {

  pessoas: Pessoa[] = [
    {
      nome: "Manoel Pinheiro",
      cidade: "Uberlandia",
      estado: "MG",
      ativo: true
    },
    {
      nome: "Sebastiao da Silva",
      cidade: "Sao paulo",
      estado: "SP",
      ativo: false
    },
    {
      nome: "Carla Souza",
      cidade: "Floripa",
      estado: "SC",
      ativo: true
    },
    {
      nome: "Luis Pereira",
      cidade: "Curitiba",
      estado: "PR",
      ativo: true
    },
    {
      nome: "Vilmar Anfrade",
      cidade: "Rio de Janeira",
      estado: "RJ",
      ativo: false
    },
    {
      nome: "Paulo Andrade",
      cidade: "Marabá",
      estado: "PA",
      ativo: false
    },
    {
      nome: "Alguma pessoa",
      cidade: "Brasilia",
      estado: "DF",
      ativo: true
    }
  ]  
}
