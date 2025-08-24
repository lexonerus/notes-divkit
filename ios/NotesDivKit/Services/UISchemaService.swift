import Foundation

class UISchemaService {
    static let shared = UISchemaService()
    
    private let baseURL = "http://localhost:8080/api/ui"
    
    private init() {}
    
    // MARK: - Получение UI схемы экрана
    func getScreenSchema(screenName: String, params: [String: String] = [:]) async throws -> UISchemaResponse {
        var components = URLComponents(string: "\(baseURL)/screen/\(screenName)")!
        
        if !params.isEmpty {
            components.queryItems = params.map { URLQueryItem(name: $0.key, value: $0.value) }
        }
        
        guard let url = components.url else {
            throw APIError.invalidURL
        }
        
        let request = URLRequest(url: url)
        let (data, response) = try await URLSession.shared.data(for: request)
        
        guard let httpResponse = response as? HTTPURLResponse else {
            throw APIError.invalidResponse
        }
        
        guard httpResponse.statusCode == 200 else {
            throw APIError.httpError(statusCode: httpResponse.statusCode)
        }
        
        return try JSONDecoder().decode(UISchemaResponse.self, from: data)
    }
    
    // MARK: - Выполнение действия
    func executeAction(_ action: ActionRequest) async throws -> ActionResponse {
        guard let url = URL(string: "\(baseURL)/action") else {
            throw APIError.invalidURL
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.httpBody = try JSONEncoder().encode(action)
        
        let (data, response) = try await URLSession.shared.data(for: request)
        
        guard let httpResponse = response as? HTTPURLResponse else {
            throw APIError.invalidResponse
        }
        
        guard httpResponse.statusCode == 200 else {
            throw APIError.httpError(statusCode: httpResponse.statusCode)
        }
        
        return try JSONDecoder().decode(ActionResponse.self, from: data)
    }
    
    // MARK: - Проверка здоровья Backend
    func checkHealth() async throws -> String {
        guard let url = URL(string: "\(baseURL)/health") else {
            throw APIError.invalidURL
        }
        
        let request = URLRequest(url: url)
        let (data, response) = try await URLSession.shared.data(for: request)
        
        guard let httpResponse = response as? HTTPURLResponse else {
            throw APIError.invalidResponse
        }
        
        guard httpResponse.statusCode == 200 else {
            throw APIError.httpError(statusCode: httpResponse.statusCode)
        }
        
        guard let responseString = String(data: data, encoding: .utf8) else {
            throw APIError.invalidData
        }
        
        return responseString
    }
}

// MARK: - API Errors
enum APIError: Error, LocalizedError {
    case invalidURL
    case invalidResponse
    case invalidData
    case httpError(statusCode: Int)
    
    var errorDescription: String? {
        switch self {
        case .invalidURL:
            return "Invalid URL"
        case .invalidResponse:
            return "Invalid response"
        case .invalidData:
            return "Invalid data"
        case .httpError(let statusCode):
            return "HTTP error: \(statusCode)"
        }
    }
}
