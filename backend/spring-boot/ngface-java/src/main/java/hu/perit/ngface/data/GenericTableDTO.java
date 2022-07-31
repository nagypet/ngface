/*
 * Copyright (c) 2022. Innodox Technologies Zrt.
 * All rights reserved.
 */

package hu.perit.ngface.data;

import hu.perit.ngface.widget.table.Paginator;
import hu.perit.ngface.widget.table.ValueSet;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GenericTableDTO<T>
{
    private List<T> rows = new ArrayList<>();
    private String notification;
}
