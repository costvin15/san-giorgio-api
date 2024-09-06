insert into clients (client_id) values
    ('76a75e23-49a8-48de-9ac1-966cf1b4120d');

insert into payments (payment_id, client, amount_paid, payment_value, payment_status) values
    ('8f9259b0-52f8-4cf0-b816-c231778bef51', '76a75e23-49a8-48de-9ac1-966cf1b4120d', 5418.0298, 7423.3621, 'PARTIAL'),
    ('8864e003-5c7c-430c-8bdf-f9f27f353ee8', '76a75e23-49a8-48de-9ac1-966cf1b4120d', 63912.9841, 351258.3522, 'PARTIAL'),
    ('7d90dbf0-d005-4454-8144-2afe500c77e5', '76a75e23-49a8-48de-9ac1-966cf1b4120d', 0.0, 94390.6055, 'PARTIAL');
