import com.example.mercadolibromobile.models.Categoria;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriaApi {
    @GET("categorias/")
    Call<List<Categoria>> getCategorias();
}
