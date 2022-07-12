package Stellaburgers;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Ingredient {
    public boolean success;
    public List<Data> data;
}