# Agile Tools API

A Spring Boot REST API for managing agile teams and capacity planning. This API provides comprehensive functionality for team management, member tracking, and leave management to support effective sprint planning and capacity management.

## Features

### 🏢 Team Management
- Create, read, update, and delete teams
- Track team members and their relationships
- Get team details with member information

### 👥 Team Member Management
- Manage team members with personal details
- Track member jurisdiction for holiday management
- Set individual capacity percentages
- Associate members with teams

### 📅 Leave Management
- Track various types of leave (annual, sick, personal, public holidays, etc.)
- Prevent overlapping leave entries
- Query leaves by team member, team, or date period
- Support for jurisdiction-specific public holidays

### 📊 Capacity Planning
- Calculate team capacity based on member availability
- Track leave periods that affect sprint capacity
- Support for partial capacity members
- Jurisdiction-aware holiday tracking

## Technology Stack

- **Java 21** - Latest LTS version
- **Spring Boot 3.3.3** - Application framework
- **Spring Data JPA** - Data access layer
- **H2 Database** - In-memory database for development
- **Maven** - Build and dependency management
- **Bean Validation** - Input validation

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/naidoort/agile-tools-api.git
   cd agile-tools-api
   ```

2. Run the application:
   ```bash
   mvn spring-boot:run
   ```

3. The API will be available at `http://localhost:8080`

### Database Console

Access the H2 database console at `http://localhost:8080/h2-console`

**Connection details:**
- JDBC URL: `jdbc:h2:mem:agiletools`
- Username: `sa`
- Password: (leave blank)

## API Documentation

### Base URL
```
http://localhost:8080/api
```

### Teams Endpoints

#### Get All Teams
```http
GET /api/teams
```

#### Get Team by ID
```http
GET /api/teams/{id}
```
Returns team with member details.

#### Create Team
```http
POST /api/teams
Content-Type: application/json

{
  "name": "Development Team Alpha",
  "description": "Main development team for product features"
}
```

#### Update Team
```http
PUT /api/teams/{id}
Content-Type: application/json

{
  "name": "Updated Team Name",
  "description": "Updated description"
}
```

#### Delete Team
```http
DELETE /api/teams/{id}
```

### Team Members Endpoints

#### Get All Team Members
```http
GET /api/team-members
```

#### Get Members by Team
```http
GET /api/team-members/team/{teamId}
```

#### Get Team Member by ID
```http
GET /api/team-members/{id}
```

#### Create Team Member
```http
POST /api/team-members
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "jurisdiction": "US",
  "capacityPercentage": 100.0,
  "teamId": 1
}
```

#### Update Team Member
```http
PUT /api/team-members/{id}
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Smith",
  "email": "john.smith@example.com",
  "jurisdiction": "US",
  "capacityPercentage": 80.0,
  "teamId": 1
}
```

#### Delete Team Member
```http
DELETE /api/team-members/{id}
```

### Leave Endpoints

#### Get All Leaves
```http
GET /api/leaves
```

#### Get Leaves by Team Member
```http
GET /api/leaves/member/{teamMemberId}
```

#### Get Leaves by Team
```http
GET /api/leaves/team/{teamId}
```

#### Get Team Leaves in Period
```http
GET /api/leaves/team/{teamId}/period?startDate=2024-01-01&endDate=2024-12-31
```

#### Create Leave
```http
POST /api/leaves
Content-Type: application/json

{
  "startDate": "2024-12-25",
  "endDate": "2024-12-25",
  "leaveType": "PUBLIC_HOLIDAY",
  "description": "Christmas Day",
  "teamMemberId": 1
}
```

#### Update Leave
```http
PUT /api/leaves/{id}
Content-Type: application/json

{
  "startDate": "2024-12-24",
  "endDate": "2024-12-26",
  "leaveType": "ANNUAL_LEAVE",
  "description": "Christmas vacation",
  "teamMemberId": 1
}
```

#### Delete Leave
```http
DELETE /api/leaves/{id}
```

### Leave Types

The API supports the following leave types:
- `ANNUAL_LEAVE` - Paid time off
- `SICK_LEAVE` - Medical leave
- `PERSONAL_LEAVE` - Personal time off
- `PUBLIC_HOLIDAY` - National/regional holidays
- `CONFERENCE` - Professional development
- `OTHER` - Other types of leave

## Data Models

### Team
```json
{
  "id": 1,
  "name": "Development Team",
  "description": "Main development team",
  "members": [...],
  "createdAt": "2024-09-14T10:30:00",
  "updatedAt": "2024-09-14T10:30:00"
}
```

### Team Member
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "jurisdiction": "US",
  "capacityPercentage": 100.0,
  "teamId": 1,
  "teamName": "Development Team",
  "leaves": [...],
  "createdAt": "2024-09-14T10:30:00",
  "updatedAt": "2024-09-14T10:30:00"
}
```

### Leave
```json
{
  "id": 1,
  "startDate": "2024-12-25",
  "endDate": "2024-12-25",
  "leaveType": "PUBLIC_HOLIDAY",
  "description": "Christmas Day",
  "teamMemberId": 1,
  "teamMemberName": "John Doe",
  "createdAt": "2024-09-14T10:30:00",
  "updatedAt": "2024-09-14T10:30:00"
}
```

## Validation Rules

### Team
- Name is required and must be unique
- Description is optional

### Team Member
- First name and last name are required
- Email is required, must be valid format, and unique
- Jurisdiction is optional but recommended for holiday tracking
- Capacity percentage defaults to 100.0

### Leave
- Start date and end date are required
- Start date cannot be after end date
- Leave cannot overlap with existing leave for the same team member
- Team member must exist

## Development

### Running Tests
```bash
mvn test
```

### Building the Project
```bash
mvn clean install
```

### Code Structure

```
src/main/java/com/agiletools/
├── controller/          # REST controllers
├── dto/                # Data Transfer Objects
├── model/              # JPA entities
├── repository/         # Data access layer
└── service/            # Business logic layer
```

## Configuration

The application uses Spring Boot's auto-configuration with these key settings:

- **Server Port**: 8080
- **Database**: H2 in-memory database
- **JPA**: Hibernate with create-drop strategy for development
- **CORS**: Enabled for `http://localhost:3000` (React frontend)

## Future Enhancements

- [ ] Authentication and authorization
- [ ] PostgreSQL production database configuration
- [ ] Docker containerization
- [ ] API versioning
- [ ] Swagger/OpenAPI documentation
- [ ] Capacity calculation endpoints
- [ ] Sprint planning integration
- [ ] Email notifications for leave approvals
- [ ] Reporting and analytics endpoints

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is open source and available under the [MIT License](LICENSE).

## Support

For support and questions, please open an issue in the GitHub repository.

---

🤖 Generated with [Claude Code](https://claude.ai/code)