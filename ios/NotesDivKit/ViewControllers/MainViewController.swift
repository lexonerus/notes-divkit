import UIKit
import DivKit

class MainViewController: UIViewController {
    
    // MARK: - UI Components
	let divkitComponents = DivKitComponents()
    private lazy var divView = DivView(divKitComponents: divkitComponents)
    private let loadingIndicator = UIActivityIndicatorView(style: .large)
    private let errorLabel = UILabel()
    
    // MARK: - Properties
    private let uiService = UISchemaService.shared
    private var currentScreen: String = "notes_list"
    private var currentSchema: UISchemaResponse?
    
    // MARK: - Lifecycle
    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
        loadInitialScreen()
    }
    
    // MARK: - UI Setup
    private func setupUI() {
        view.backgroundColor = .systemBackground

        divView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(divView)
        
        // Настройка индикатора загрузки
        loadingIndicator.translatesAutoresizingMaskIntoConstraints = false
        loadingIndicator.hidesWhenStopped = true
        view.addSubview(loadingIndicator)
        
        // Настройка лейбла ошибки
        errorLabel.translatesAutoresizingMaskIntoConstraints = false
        errorLabel.textAlignment = .center
        errorLabel.numberOfLines = 0
        errorLabel.textColor = .systemRed
        errorLabel.isHidden = true
        view.addSubview(errorLabel)
        
        setupConstraints()
        // Пока не настраиваем DivKit callbacks - используем простой UI
    }
    
    private func setupConstraints() {
        NSLayoutConstraint.activate([
            // DivKit View
            divView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
            divView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            divView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            divView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            
            // Loading Indicator
            loadingIndicator.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            loadingIndicator.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            
            // Error Label
            errorLabel.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            errorLabel.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            errorLabel.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 20),
            errorLabel.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -20)
        ])
    }
    
    // MARK: - Screen Loading
    private func loadInitialScreen() {
        loadScreen("notes_list")
    }
    
    private func loadScreen(_ screenName: String, params: [String: String] = [:]) {
        currentScreen = screenName
        showLoading(true)
        hideError()
        
        Task {
            do {
                let schema = try await uiService.getScreenSchema(screenName: screenName, params: params)
                await MainActor.run {
                    self.currentSchema = schema
                    self.displayScreen(schema)
                    self.showLoading(false)
                }
            } catch {
                await MainActor.run {
                    self.showError("Ошибка загрузки экрана: \(error.localizedDescription)")
                    self.showLoading(false)
                }
            }
        }
    }
    
    private func displayScreen(_ schema: UISchemaResponse) {
        // Пока отображаем как простой UI для тестирования
        showSimpleUI(from: schema)
    }
    
    // MARK: - Simple UI Display (временное решение)
    private func showSimpleUI(from schema: UISchemaResponse) {
        // Скрываем DivKit view и показываем простой UI
        divView.isHidden = true
        
        // Удаляем предыдущий UI если есть
        view.subviews.forEach { subview in
            if subview != divView && subview != loadingIndicator && subview != errorLabel {
                subview.removeFromSuperview()
            }
        }
        
        // Создаем простой UI на основе схемы
        let stackView = UIStackView()
        stackView.axis = .vertical
        stackView.spacing = 10
        stackView.translatesAutoresizingMaskIntoConstraints = false
        
        // Добавляем заголовок
        if let headerItem = schema.ui.items.first(where: { $0.type == "header" }) {
            let titleLabel = UILabel()
            titleLabel.text = headerItem.title ?? "Заголовок"
            titleLabel.font = UIFont.boldSystemFont(ofSize: 24)
            titleLabel.textAlignment = .center
            stackView.addArrangedSubview(titleLabel)
            
            if let subtitle = headerItem.subtitle {
                let subtitleLabel = UILabel()
                subtitleLabel.text = subtitle
                subtitleLabel.font = UIFont.systemFont(ofSize: 16)
                subtitleLabel.textAlignment = .center
                subtitleLabel.textColor = .secondaryLabel
                stackView.addArrangedSubview(subtitleLabel)
            }
        }
        
        // Добавляем заметки
        for item in schema.ui.items where item.type == "note_item" {
            let noteView = createNoteView(from: item)
            stackView.addArrangedSubview(noteView)
        }
        
        // Добавляем кнопки
        for item in schema.ui.items where item.type == "button" {
            let button = createButton(from: item)
            stackView.addArrangedSubview(button)
        }
        
        // Добавляем в view
        view.addSubview(stackView)
        NSLayoutConstraint.activate([
            stackView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 20),
            stackView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 20),
            stackView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -20)
        ])
    }
    
    private func createNoteView(from item: UIItem) -> UIView {
        let containerView = UIView()
        containerView.backgroundColor = .systemGray6
        containerView.layer.cornerRadius = 8
        containerView.translatesAutoresizingMaskIntoConstraints = false
        
        let titleLabel = UILabel()
        titleLabel.text = item.title
        titleLabel.font = UIFont.boldSystemFont(ofSize: 18)
        titleLabel.translatesAutoresizingMaskIntoConstraints = false
        
        let subtitleLabel = UILabel()
        subtitleLabel.text = item.subtitle
        subtitleLabel.font = UIFont.systemFont(ofSize: 14)
        subtitleLabel.textColor = .secondaryLabel
        subtitleLabel.numberOfLines = 2
        subtitleLabel.translatesAutoresizingMaskIntoConstraints = false
        
        containerView.addSubview(titleLabel)
        containerView.addSubview(subtitleLabel)
        
        NSLayoutConstraint.activate([
            titleLabel.topAnchor.constraint(equalTo: containerView.topAnchor, constant: 12),
            titleLabel.leadingAnchor.constraint(equalTo: containerView.leadingAnchor, constant: 12),
            titleLabel.trailingAnchor.constraint(equalTo: containerView.trailingAnchor, constant: -12),
            
            subtitleLabel.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: 4),
            subtitleLabel.leadingAnchor.constraint(equalTo: containerView.leadingAnchor, constant: 12),
            subtitleLabel.trailingAnchor.constraint(equalTo: containerView.trailingAnchor, constant: -12),
            subtitleLabel.bottomAnchor.constraint(equalTo: containerView.bottomAnchor, constant: -12)
        ])
        
        // Добавляем действие нажатия
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(noteTapped(_:)))
        containerView.addGestureRecognizer(tapGesture)
        containerView.tag = item.id.hashValue // Простой способ идентификации
        
        return containerView
    }
    
    private func createButton(from item: UIItem) -> UIButton {
        let button = UIButton(type: .system)
        button.setTitle(item.text, for: .normal)
        button.backgroundColor = .systemBlue
        button.setTitleColor(.white, for: .normal)
        button.layer.cornerRadius = 8
        button.translatesAutoresizingMaskIntoConstraints = false
        
        NSLayoutConstraint.activate([
            button.heightAnchor.constraint(equalToConstant: 44)
        ])
        
        // Добавляем действие
        button.addTarget(self, action: #selector(buttonTapped(_:)), for: .touchUpInside)
        button.tag = item.id.hashValue
        
        return button
    }
    
    // MARK: - Actions
    @objc private func noteTapped(_ gesture: UITapGestureRecognizer) {
        guard let containerView = gesture.view else { return }
        let noteId = containerView.tag
        
        // Находим заметку по ID
        if let noteItem = currentSchema?.ui.items.first(where: { $0.id.hashValue == noteId }) {
            // Переходим к просмотру заметки
            if let noteIdValue = noteItem.data?["note_id"]?.value as? Int {
                loadScreen("note_view", params: ["id": "\(noteIdValue)"])
            }
        }
    }
    
    @objc private func buttonTapped(_ sender: UIButton) {
        let buttonId = sender.tag
        
        // Находим кнопку по ID
        if let buttonItem = currentSchema?.ui.items.first(where: { $0.id.hashValue == buttonId }) {
            switch buttonItem.id {
            case "create_note_btn":
                loadScreen("note_create")
            default:
                print("Unknown button: \(buttonItem.id)")
            }
        }
    }
    
    // MARK: - UI State Management
    private func showLoading(_ show: Bool) {
        if show {
            loadingIndicator.startAnimating()
        } else {
            loadingIndicator.stopAnimating()
        }
    }
    
    private func showError(_ message: String) {
        errorLabel.text = message
        errorLabel.isHidden = false
        divView.isHidden = true
    }
    
    private func hideError() {
        errorLabel.isHidden = true
        divView.isHidden = false
    }
    
    private func showSuccessMessage(_ message: String) {
        let alert = UIAlertController(title: "Успех", message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default))
        present(alert, animated: true)
    }
}
