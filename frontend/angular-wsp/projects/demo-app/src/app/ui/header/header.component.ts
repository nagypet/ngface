import {Component, OnInit} from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import {FormBaseComponent} from '../../../../../ngface/src/lib/form/form-base.component';
import {NgfaceTitlebarComponent} from '../../../../../ngface/src/lib/titlebar/ngface-titlebar/ngface-titlebar.component';
import {Ngface} from '../../../../../ngface/src/lib/ngface-models';
import {TitlebarService} from '../../core/services/titlebar.service';
import {DeviceTypeService} from '../../../../../ngface/src/lib/services/device-type.service';
import {ResponsiveClassDirective} from '../../../../../ngface/src/lib/directives/responsive-class-directive';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss'],
    imports: [
        RouterOutlet,
        NgfaceTitlebarComponent,
        ResponsiveClassDirective
    ],
    standalone: true
})
export class HeaderComponent extends FormBaseComponent implements OnInit
{

    constructor(
        private router: Router,
        private titlebarService: TitlebarService,
        public deviceTypeService: DeviceTypeService
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
