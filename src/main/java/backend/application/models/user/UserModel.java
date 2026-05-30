package backend.application.models.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import backend.application.models.TaskModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "users")
public class UserModel {
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private UserRole role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TaskModel> tasks = new ArrayList<>() {};
}
