import com.example.mercadolibromobile.models.Book;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookApi {
    @GET("libros/")
    Call<List<Book>> getBooks(@Query("titulo") String titulo, @Query("id_categoria__nombre_categoria") String categoria);
}

