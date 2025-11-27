import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { NzMessageModule } from 'ng-zorro-antd/message';
import { NzDropDownModule } from 'ng-zorro-antd/dropdown';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzBreadCrumbModule } from 'ng-zorro-antd/breadcrumb';
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { NzSkeletonModule } from 'ng-zorro-antd/skeleton';
import { NzAvatarModule } from 'ng-zorro-antd/avatar';
import { NzPaginationModule } from 'ng-zorro-antd/pagination';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzTagModule } from 'ng-zorro-antd/tag';

import { IconDefinition } from '@ant-design/icons-angular';
import { SyncOutline, CheckCircleTwoTone, CloseCircleTwoTone } from '@ant-design/icons-angular/icons';

const icons: IconDefinition[] = [SyncOutline, CheckCircleTwoTone, CloseCircleTwoTone];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NzLayoutModule,
    NzMenuModule,
    NzIconModule.forRoot(icons),
    NzButtonModule,
    NzInputModule,
    NzFormModule,
    NzTableModule,
    NzModalModule,
    NzMessageModule,
    NzDropDownModule,
    NzSelectModule,
    NzCardModule,
    NzBreadCrumbModule,
    NzDividerModule,
    NzSkeletonModule,
    NzAvatarModule,
    NzPaginationModule,
    NzDatePickerModule,
    NzTagModule
  ],
  exports: [
    FormsModule,
    ReactiveFormsModule,
    NzLayoutModule,
    NzMenuModule,
    NzIconModule,
    NzButtonModule,
    NzInputModule,
    NzFormModule,
    NzTableModule,
    NzModalModule,
    NzMessageModule,
    NzDropDownModule,
    NzSelectModule,
    NzCardModule,
    NzBreadCrumbModule,
    NzDividerModule,
    NzSkeletonModule,
    NzAvatarModule,
    NzPaginationModule,
    NzDatePickerModule,
    NzTagModule
  ]
})
export class NgZorroAntdModule {}
