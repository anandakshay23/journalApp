# 📖 Journal App

A personal journaling application built with modern web technologies.

## 🚀 Getting Started

### Prerequisites
- Docker installed on your system
- Java (if running the app locally)

### Database Setup with MongoDB

This application uses MongoDB as its database, running in a Docker container for easy setup and management.

#### 1. Create MongoDB Container

```bash
docker run -d \
  --name mongodb \
  -p 27017:27017 \
  -v $HOME/docker-volumes/mongodb/data:/data/db \
  -e MONGO_INITDB_ROOT_USERNAME=admin \
  -e MONGO_INITDB_ROOT_PASSWORD=secret \
  mongo
```

#### 2. Container Management

**Start the MongoDB service:**
```bash
docker start mongodb
```

**Stop the MongoDB service:**
```bash
docker stop mongodb
```

**Check container status:**
```bash
docker ps -a
```

#### 3. Database Access

**Connect to MongoDB shell:**
```bash
docker exec -it mongodb mongosh -u admin -p secret
```

**Alternative connection string for your app:**
```
mongodb://admin:secret@localhost:27017/journalapp?authSource=admin
```
## 🚀 API Testing

A Postman collection is included in the repository for easy API testing and development.

**Import the collection:**
1. Open Postman
2. Click "Import" button
3. Select the `journalApp.postman_collection.json` file from the project root
4. The collection will be imported with all available endpoints

**Collection includes:**
- Authentication endpoints
- Journal entry CRUD operations
- User management endpoints
- Sample requests and responses

**Using the collection:**
- Run individual requests or the entire collection

## 📝 Features

- Create and manage personal journal entries
- Secure authentication
- Data persistence with MongoDB

**MongoDB connection issues:**
- Ensure Docker is running
- Check if the container is started: `docker ps`
- Verify port 27017 is not in use by another service

**Container not starting:**
- Check Docker logs: `docker logs mongodb`
- Ensure sufficient disk space for the volume mount

## 📧 Contact

For questions or support, please open an issue in the GitHub repository.

---

*Happy journaling! ✍️*