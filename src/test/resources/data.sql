INSERT INTO clients (client_id) VALUES
    ('5dc12ebf-c438-4012-b098-fbf289cc9279'),
    ('49f0592c-6e70-4b64-9ee8-3bd127e9e5a8'),
    ('77782320-356b-4a5b-b55f-1df71e0a8c47'),
    ('080ee758-af2e-496d-97b4-d2344a821819'),
    ('ae14e23a-4104-455c-bfe9-2720d9e5e75b'),
    ('c8bfde39-3895-43ab-8736-a2ab7c43ea7b');

INSERT INTO payments (payment_id, amount_paid, payment_value, payment_status, client) VALUES
    ('052d3e66-f9ac-478f-9121-c894ac429a17', 0, 100, 'PARTIAL', '5dc12ebf-c438-4012-b098-fbf289cc9279'),
    ('94f3f77c-0907-4d14-bf29-0d91dfc2353a', 0, 100, 'PARTIAL', '5dc12ebf-c438-4012-b098-fbf289cc9279'),
    ('8bf061c4-4ec0-43d3-9409-a05290ee1892', 100, 100, 'TOTAL', '49f0592c-6e70-4b64-9ee8-3bd127e9e5a8'),
    ('f4a4c673-6bfc-49af-8cbf-71492dce1d9b', 100, 100, 'TOTAL', '49f0592c-6e70-4b64-9ee8-3bd127e9e5a8'),
    ('df5239dd-f0af-4331-8492-50305b3ec913', 100, 1000, 'PARTIAL', '77782320-356b-4a5b-b55f-1df71e0a8c47'),
    ('8cb91c5e-fbe6-4b63-86bb-2bfb71eca342', 100, 1000, 'PARTIAL', '77782320-356b-4a5b-b55f-1df71e0a8c47'),
    ('a7161c19-ae37-4231-90b7-87dbdc32288c', 0, 100, 'PARTIAL', '080ee758-af2e-496d-97b4-d2344a821819'),
    ('7503a4bc-62b1-47a9-8baf-d2bacff8fb40', 0, 100, 'PARTIAL', '080ee758-af2e-496d-97b4-d2344a821819'),
    ('f055f7d8-cf87-4d5e-ac86-ce18772788b8', 100, 100, 'TOTAL', 'ae14e23a-4104-455c-bfe9-2720d9e5e75b'),
    ('30add840-09bb-4718-9ffe-42132dbcc495', 50, 100, 'PARTIAL', 'c8bfde39-3895-43ab-8736-a2ab7c43ea7b');
