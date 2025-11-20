import { Component } from '@angular/core';
import { CreditoService } from '../../../../services/credito.service';
import { TableModule } from 'primeng/table';
import { InputGroupModule } from 'primeng/inputgroup';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { RadioButtonModule } from 'primeng/radiobutton';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';


@Component({
  selector: 'app-perfil',
  templateUrl: './consulta-credito.component.html',
  styleUrls: ['./consulta-credito.component.scss'],
  imports: [CommonModule,TableModule,InputGroupModule,FormsModule,ButtonModule,RadioButtonModule,ToastModule],
  providers: [MessageService]
})
export class ConsultaCreditoComponent {

  creditos !: any[];
  tiposConsulta = ['Número de crédito', 'Número de NFSE'];
  tipoConsultaSelecionado = 'Número de crédito';
  numeroConsultado!: string;

  constructor(private creditoService: CreditoService,
    private messageService: MessageService
  ) {}

  ngOnInit() {
  }

  consultarCredito() {
    if (!this.numeroConsultado || this.numeroConsultado.trim() === '') {
      this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Please fill the credit or invoice number.' });
      return;
    }

    if(this.tipoConsultaSelecionado === 'Número de crédito') {
      this.creditoService.obterPorNumeroCredito(this.numeroConsultado).subscribe((credito: any) => {
        this.creditos = [credito];
      });
    } else {
      this.creditoService.obterPorNumeroNfse(this.numeroConsultado).subscribe((creditos: any) => {
        this.creditos = [...creditos];
        if(this.creditos.length === 0) {
          this.messageService.add({ severity: 'info', summary: 'No credit found', detail: `No credit found for the invoice '${this.numeroConsultado}'.` });
        }
      });
    }
  }

}
