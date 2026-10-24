// TaskTrack frontend. Plain JavaScript, no libraries.
// All data lives in the backend: every change goes through the REST API.

const API_URL = '/api/tasks';

// ---------- State ----------
let tasks = [];          // latest list from the server
let currentFilter = 'ALL';
let editingId = null;    // id of the task being edited, or null when adding
let isLoading = false;
let isSubmitting = false;

// ---------- Element references ----------
const form = document.getElementById('task-form');
const formFields = document.getElementById('form-fields');
const formHeading = document.getElementById('form-heading');
const titleInput = document.getElementById('title');
const descriptionInput = document.getElementById('description');
const priorityInput = document.getElementById('priority');
const dueDateInput = document.getElementById('dueDate');
const submitButton = document.getElementById('submit-button');
const cancelButton = document.getElementById('cancel-button');
const taskList = document.getElementById('task-list');
const loadingText = document.getElementById('loading');
const emptyText = document.getElementById('empty');
const messageBox = document.getElementById('message');
const filterButtons = document.querySelectorAll('.filter-button');

// ---------- API helper ----------

// Sends a request and returns parsed JSON (or null for 204).
// Throws an Error with a user-friendly message on failure.
async function api(path, options = {}) {
  let response;
  try {
    response = await fetch(path, {
      headers: { 'Content-Type': 'application/json' },
      ...options,
    });
  } catch (networkError) {
    throw new Error('Cannot reach the TaskTrack server. Is the backend running?');
  }

  if (response.status === 204) {
    return null;
  }

  let body = null;
  try {
    body = await response.json();
  } catch (parseError) {
    // The response had no JSON body; fall through to the generic message below.
  }

  if (!response.ok) {
    throw new Error(buildErrorMessage(body, response.status));
  }
  return body;
}

// Turns the backend's error JSON into one readable sentence.
function buildErrorMessage(body, status) {
  if (body && body.details && Object.keys(body.details).length > 0) {
    return Object.values(body.details).join('. ');
  }
  if (body && body.message) {
    return body.message;
  }
  return 'Request failed (HTTP ' + status + ').';
}

// ---------- Messages and UI state ----------

function showMessage(text, type) {
  messageBox.textContent = text;
  messageBox.className = 'message is-' + type;
  messageBox.hidden = false;
}

function clearMessage() {
  messageBox.hidden = true;
  messageBox.textContent = '';
}

function setLoading(value) {
  isLoading = value;
  render();
}

function setSubmitting(value) {
  isSubmitting = value;
  formFields.disabled = value;
  submitButton.textContent = value ? 'Saving…' : (editingId === null ? 'Add task' : 'Save changes');
}

// ---------- Loading data ----------

async function loadTasks() {
  setLoading(true);
  try {
    tasks = await api(API_URL);
  } catch (error) {
    tasks = [];
    showMessage(error.message, 'error');
  } finally {
    setLoading(false);
  }
}

// ---------- Rendering ----------

function render() {
  renderSummary();

  loadingText.hidden = !isLoading;

  const visibleTasks = tasks.filter((task) => currentFilter === 'ALL' || task.status === currentFilter);

  emptyText.hidden = isLoading || visibleTasks.length > 0;
  if (!emptyText.hidden) {
    emptyText.textContent = 'No tasks to show.';
  }

  taskList.replaceChildren(...visibleTasks.map(createTaskElement));
}

function renderSummary() {
  const completed = tasks.filter((task) => task.status === 'COMPLETED').length;
  document.getElementById('count-total').textContent = tasks.length;
  document.getElementById('count-completed').textContent = completed;
  document.getElementById('count-pending').textContent = tasks.length - completed;
}

// Small helper so we never build HTML strings from user input.
function el(tag, className, text) {
  const element = document.createElement(tag);
  if (className) element.className = className;
  if (text !== undefined) element.textContent = text;
  return element;
}

function capitalize(value) {
  return value.charAt(0) + value.slice(1).toLowerCase();
}

function formatDate(isoDate) {
  // isoDate looks like "2026-10-30"; build a local date to avoid timezone shifts.
  const [year, month, day] = isoDate.split('-').map(Number);
  return new Date(year, month - 1, day).toLocaleDateString(undefined, {
    year: 'numeric', month: 'short', day: 'numeric',
  });
}

function createTaskElement(task) {
  const isCompleted = task.status === 'COMPLETED';

  const item = el('li', 'task priority-' + task.priority.toLowerCase() + (isCompleted ? ' is-completed' : ''));
  item.appendChild(el('h3', 'task-title', task.title));

  if (task.description) {
    item.appendChild(el('p', 'task-description', task.description));
  }

  const meta = el('div', 'task-meta');
  meta.appendChild(el('span', 'badge badge-' + task.status.toLowerCase(), capitalize(task.status)));
  meta.appendChild(el('span', 'badge badge-priority', capitalize(task.priority) + ' priority'));
  if (task.dueDate) {
    meta.appendChild(el('span', 'due-date', 'Due ' + formatDate(task.dueDate)));
  }
  item.appendChild(meta);

  const actions = el('div', 'task-actions');

  const toggleButton = el('button', 'button', isCompleted ? 'Mark pending' : 'Mark complete');
  toggleButton.type = 'button';
  toggleButton.addEventListener('click', () => toggleStatus(task));

  const editButton = el('button', 'button', 'Edit');
  editButton.type = 'button';
  editButton.setAttribute('aria-label', 'Edit task: ' + task.title);
  editButton.addEventListener('click', () => startEditing(task));

  const deleteButton = el('button', 'button button-danger', 'Delete');
  deleteButton.type = 'button';
  deleteButton.setAttribute('aria-label', 'Delete task: ' + task.title);
  deleteButton.addEventListener('click', () => deleteTask(task));

  actions.append(toggleButton, editButton, deleteButton);
  item.appendChild(actions);
  return item;
}

// ---------- Form handling ----------

function resetForm() {
  editingId = null;
  form.reset();
  priorityInput.value = 'MEDIUM';
  formHeading.textContent = 'Add a task';
  submitButton.textContent = 'Add task';
  cancelButton.hidden = true;
}

function startEditing(task) {
  editingId = task.id;
  titleInput.value = task.title;
  descriptionInput.value = task.description || '';
  priorityInput.value = task.priority;
  dueDateInput.value = task.dueDate || '';
  formHeading.textContent = 'Edit task';
  submitButton.textContent = 'Save changes';
  cancelButton.hidden = false;
  clearMessage();
  titleInput.focus();
}

function readForm() {
  return {
    title: titleInput.value.trim(),
    description: descriptionInput.value.trim() || null,
    priority: priorityInput.value,
    dueDate: dueDateInput.value || null,
  };
}

form.addEventListener('submit', async (event) => {
  event.preventDefault();
  if (isSubmitting) return;
  clearMessage();

  const data = readForm();
  if (!data.title) {
    showMessage('Title is required.', 'error');
    titleInput.focus();
    return;
  }

  setSubmitting(true);
  try {
    if (editingId === null) {
      await api(API_URL, { method: 'POST', body: JSON.stringify(data) });
      showMessage('Task added.', 'success');
    } else {
      // PUT replaces all editable fields, so keep the task's current status.
      const existing = tasks.find((task) => task.id === editingId);
      data.status = existing ? existing.status : 'PENDING';
      await api(API_URL + '/' + editingId, { method: 'PUT', body: JSON.stringify(data) });
      showMessage('Task updated.', 'success');
    }
    resetForm();
    await loadTasks();
  } catch (error) {
    showMessage(error.message, 'error');
  } finally {
    setSubmitting(false);
  }
});

cancelButton.addEventListener('click', () => {
  resetForm();
  clearMessage();
});

// ---------- Task actions ----------

async function toggleStatus(task) {
  clearMessage();
  const newStatus = task.status === 'COMPLETED' ? 'PENDING' : 'COMPLETED';
  const body = {
    title: task.title,
    description: task.description,
    priority: task.priority,
    dueDate: task.dueDate,
    status: newStatus,
  };
  try {
    await api(API_URL + '/' + task.id, { method: 'PUT', body: JSON.stringify(body) });
    showMessage(newStatus === 'COMPLETED' ? 'Task marked complete.' : 'Task marked pending.', 'success');
    await loadTasks();
  } catch (error) {
    showMessage(error.message, 'error');
  }
}

async function deleteTask(task) {
  if (!window.confirm('Delete "' + task.title + '"? This cannot be undone.')) {
    return;
  }
  clearMessage();
  try {
    await api(API_URL + '/' + task.id, { method: 'DELETE' });
    if (editingId === task.id) resetForm();
    showMessage('Task deleted.', 'success');
    await loadTasks();
  } catch (error) {
    showMessage(error.message, 'error');
  }
}

// ---------- Filters ----------

filterButtons.forEach((button) => {
  button.addEventListener('click', () => {
    currentFilter = button.dataset.filter;
    filterButtons.forEach((other) => {
      const active = other === button;
      other.classList.toggle('is-active', active);
      other.setAttribute('aria-pressed', String(active));
    });
    render();
  });
});

// ---------- Start ----------
loadTasks();
