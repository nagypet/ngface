import {Component, OnInit} from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import {FormBaseComponent} from '../../../../ngface/src/lib/form/form-base.component';
import {NgfaceTitlebarComponent} from '../../../../ngface/src/lib/titlebar/ngface-titlebar/ngface-titlebar.component';
import {Ngface} from '../../../../ngface/src/lib/ngface-models';
import {WidgetDemoFormService} from '../services/widget-demo-form.service';
import {TitlebarService} from '../services/titlebar.service';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss'],
    imports: [
        RouterOutlet,
        NgfaceTitlebarComponent
    ],
    standalone: true
})
export class HeaderComponent extends FormBaseComponent implements OnInit
{

    constructor(
        private router: Router,
        private titlebarService: TitlebarService,
    )
    {
        super();
    }

    ngOnInit(): void
    {
        this.titlebarService.getTitlebar().subscribe(form =>
        {
            console.log(form);
            this.formData = form;
        });
    }

    onTitlebarMenuItemClick($event: Ngface.Menu.Item): void
    {
        switch ($event.id)
        {
            case 'widgets_demo':
                this.router.navigate(['widget-demo']);
                break;

            case 'table_demo':
                this.router.navigate(['table-demo']);
                break;
        }
    }

    onTitlebarActionClick($event: Ngface.Action): void
    {

    }
}
