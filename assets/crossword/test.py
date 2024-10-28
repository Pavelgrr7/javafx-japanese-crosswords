import random

# Set the filename and the number of lines and length of each line
filename = 'test2.txt'
num_lines = 11
line_length = 11

# Generate the content: 150 lines of 150 random '1' or '0' characters
content = [''.join(random.choice('01') for _ in range(line_length)) for _ in range(num_lines)]

# Write the content to the file
with open(filename, 'w') as file:
    file.write('\n'.join(content))

print(f"File '{filename}' with {num_lines} lines has been created.")
