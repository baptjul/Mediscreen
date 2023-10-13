export default async function fetchAPI<T>(endpoint: string, method: string, data?: any): Promise<null> {
    const API_URL = 'http://localhost:9090';
    const token = localStorage.getItem('token');

    if (!token && !endpoint.includes('usersService')) {
        throw new Error('No token available');
    }

    const options: RequestInit = {
        method,
        headers: {
            'Content-Type': 'application/json',
            ...(endpoint.includes('usersService') || !token ? {} : { 'Authorization': `Bearer ${token}` }),
        },
    };

    if (data) {
        options.body = JSON.stringify(data);
    }

    const response = await fetch(`${API_URL}${endpoint}`, options);

    if (!response.ok) {
        const responseData = await response.json();
        const errorMessage = responseData.message || `Error fetching ${API_URL}${endpoint}`;
        throw new Error(errorMessage);
    }

    const responseBody = await response.text();

    if (responseBody) {
        const target = endpoint.split('/')[1];
        if (target === 'assessService' || target === 'usersService') {
            return responseBody as any
        } else {
            return JSON.parse(responseBody);
        }

    } else if (response.ok) {
        return null;
    } else {
        throw new Error('Unexpected API response');
    }
}

