package hu.perit.ngface.core.widget.input;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class AutocompleteOption implements AbstractOption
{
    private String id;
    @Setter(AccessLevel.NONE)
    private List<String> texts = new ArrayList<>();


    public AutocompleteOption(String id, String... text)
    {
        this.id = id;
        this.texts.addAll(Arrays.asList(text));
    }


    @Override
    public int compareTo(@NotNull AbstractOption o)
    {
        if (!(o instanceof AutocompleteOption other))
        {
            return -1;
        }
        CompareToBuilder compareToBuilder = new CompareToBuilder();
        for (int i = 0; i < Math.min(texts.size(), other.texts.size()); i++)
        {
            compareToBuilder.append(texts.get(i), other.texts.get(i));
        }
        return compareToBuilder.toComparison();
    }
}
