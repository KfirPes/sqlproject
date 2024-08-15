package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static Connection connection = null;

    // Private constructor prevents instantiation from other classes
    private DatabaseConnection() { }

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DatabaseConnection.class) {
                if (connection == null) {
                    try {
                        Class.forName("org.postgresql.Driver");
                        connection = DriverManager.getConnection(
                            "jdbc:postgresql://localhost:5432/prog",
                            "postgres",
                            "Kfir5471");
                    } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                
            	 e.printStackTrace();
            } finally {
               
                connection = null;
            }
        }
    }

    public static int insertCompany(String companyName) {
        int generatedId = -1;
        String sql = "INSERT INTO companies (company_name) VALUES (?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {  
            pstmt.setString(1, companyName);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {  // ���� �� �� ����� ���� ���� ���� ���� ����
                        generatedId = rs.getInt(1);  // ���� �-ID �����
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedId;
    }

    public static List<Integer> insertDepartments(List<department> departments, int companyId) {
        String sql = "INSERT INTO departments (department_name, is_syn, is_possible, company_id) VALUES (?, ?, ?, ?)";
        List<Integer> generatedIds = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        try (
             PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
        	
            for (department department : departments) {
                pstmt.setString(1, department.getName());
                pstmt.setBoolean(2, department.getSyn());
                pstmt.setBoolean(3, department.getPos());
                pstmt.setInt(4, companyId);
                pstmt.addBatch();  
            }

            pstmt.executeBatch();  

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                while (rs.next()) {
                    generatedIds.add(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return generatedIds;  // ����� ����� �-IDs ������
    }

    public static List<Integer> insertPreferences(List<preference> preferences) {
        String sql = "INSERT INTO preferences (preference_type, addition) VALUES (?, ?)";
        List<Integer> generatedIds = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (preference preference : preferences) {
                pstmt.setString(1, preference.getType());
                pstmt.setDouble(2, preference.getAdd());
                pstmt.addBatch();  // ����� ������ ������
            }

            pstmt.executeBatch();  // ����� ������ ��� ���

            // ���� �-IDs ������
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                while (rs.next()) {
                    generatedIds.add(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedIds;  // ����� ����� �-IDs ������
    }

    public static void insertDepartmentPreference(List<Integer>departmentsId,List<Integer>preferencesId) {
        String sql = "INSERT INTO departments_preferences (department_id, preference_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	int count=0;
        	for(int Ids:departmentsId)
        	{
            pstmt.setInt(1, Ids);
            pstmt.setInt(2, preferencesId.get(count));
            count++;
        	}
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> insertPossitions(List<possition> possitions) {
        String sql = "INSERT INTO roles (role_name, is_possible) VALUES (?, ?)";
        List<Integer> generatedIds = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (possition possition : possitions) {
                pstmt.setString(1, possition.getName());
                pstmt.setBoolean(2, possition.getPos());
                pstmt.addBatch();  // ����� ������ ������
            }

            pstmt.executeBatch();  // ����� ������ ��� ���

            // ���� �-IDs ������
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                while (rs.next()) {
                    generatedIds.add(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedIds;  // ����� ����� �-IDs ������
    }

    public static List<Integer> insertRoles(List<possition> possitions) {
        String sql = "INSERT INTO roles (role_name, is_possible) VALUES (?, ?)";
        List<Integer> generatedIds = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (possition possition : possitions) {
                pstmt.setString(1, possition.getName());
                pstmt.setBoolean(2, possition.getPos());
                pstmt.addBatch();  // ����� ������ ������
            }

            pstmt.executeBatch();  // ����� ������ ��� ���

            // ���� �-IDs ������
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                while (rs.next()) {
                    generatedIds.add(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedIds;  // ����� ����� �-IDs ������
    }

    public static List<Integer> insertEmployees(List<employee> employees,List<Integer>prefEmpsIds) {
        String sql = "INSERT INTO employees (employee_name, employee_type, preference_id) VALUES (?, ?, ?)";
        List<Integer> generatedIds = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        	int count=0;
            for (employee employee : employees) {
                pstmt.setString(1, employee.getName());
                pstmt.setString(2, employee.getType());
                pstmt.setInt(3, prefEmpsIds.get(count));
                pstmt.addBatch();  // ����� ������ ������
            }

            pstmt.executeBatch();  // ����� ������ ��� ���

            // ���� �-IDs ������
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                while (rs.next()) {
                    generatedIds.add(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedIds;  // ����� ����� �-IDs ������
    }

    public static List<Integer> insertDepartmentPositionPairs(List<DepartmentPositionPair> departmentPositionPairs){
        String sql = "INSERT INTO departments_roles (department_id, role_id) VALUES (?, ?)";
        List<Integer> generatedIds = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (DepartmentPositionPair pair : departmentPositionPairs) {
                pstmt.setInt(1, pair.getDepartmentId());
                pstmt.setInt(2, pair.getPositionId());
                pstmt.addBatch();  // ����� ������ ������
            }

            pstmt.executeBatch();  // ����� ������ ��� ���

            // ���� �-IDs ������
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                while (rs.next()) {
                    generatedIds.add(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedIds;  // ����� ����� �-IDs ������
    }

    public static List<company> getCompanies(String sql,Connection conn) {
      
        List<company> companies = new ArrayList<>();

        try (//Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int companyId = rs.getInt("company_id");
                System.out.println("companyId: " + companyId);
                String companyName = rs.getString("company_name");
                companies.add(new company(companyId,companyName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companies;
    }
    public static List<department> getDepartments(String sql,Connection conn) {
    	System.out.println(sql);
    	List<department> departments = new ArrayList<>();
    	//Connection conn = DatabaseConnection.getConnection();
    	try (
                PreparedStatement pstmt = conn.prepareStatement(sql);
    			
                ResultSet rs = pstmt.executeQuery()) {
            	 while (rs.next()) {
            		 String departmentName = rs.getString("department_name");
                     boolean isSyn = rs.getBoolean("is_syn");
                     boolean isPossible = rs.getBoolean("is_possible");
                     int departmentId = rs.getInt("department_id");
                     departments.add(new department(departmentId,departmentName,isSyn,isPossible));
                
            }
        } catch (SQLException e) {
        	
            e.printStackTrace();
        }

        return departments;
    }
    public List<possition> getPossitions(String sql) {
        List<possition> possitions = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int PossitionId = rs.getInt("role_id");
                String roleName = rs.getString("role_name");
                boolean isPossible = rs.getBoolean("is_possible");
                double addition = rs.getDouble("addition");
                possitions.add(new possition(PossitionId,roleName,isPossible));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return possitions;
    }
    public List<employee> getEmployees(String sql) {
        
        List<employee> employees = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             ResultSet rs = pstmt.executeQuery() ;
                while (rs.next()) {
                    int employeeId = rs.getInt("employee_id");
                    String employeeName = rs.getString("employee_name");
                    String employeeType = rs.getString("employee_type");
                    employees.add(new employee(employeeId,employeeName,employeeType));
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }
}
